import {Component, OnDestroy, OnInit} from "@angular/core";
import {Subscription} from "rxjs/Subscription";
import {MqttMessage, MqttService} from "ngx-mqtt";
import {UserService} from "../../services/user.service";
import {User} from "../../models/user";
import {environment} from "../../../environments/environment";
import {isUndefined} from "util";
import {DEFAULT_INTERRUPTSOURCES} from "@ng-idle/core";
import {HttpHeaders, HttpRequest} from "@angular/common/http";
import {AuthenticationService} from "../../services/authentication.service";
import {Router} from "@angular/router";
import {Idle} from "@ng-idle/core";
import {Keepalive} from "@ng-idle/keepalive";
import {QuerySource} from "../../sources/query.source";
import {SourceService} from "../../services/source.service";
import {Blocker} from "../../blocker";

@Component({
  moduleId: module.id,
  templateUrl: 'restaurants.query.component.html'
})

export class RestaurantsQueryComponent implements OnInit, OnDestroy {
  currentUser: User;
  userSort: User[] = new Array();
  public usersSubscription: Subscription;
  header: any;
  public blocker: Blocker;
  public source: QuerySource;

  constructor(private sourceService: SourceService,
              private userService: UserService,
              private mqttService: MqttService,
              private authenticationService: AuthenticationService,
              private router: Router,
              private idle: Idle,
              private keepAlive: Keepalive) {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    this.blocker = new Blocker();
    this.source = sourceService.createQuery('user/query').blocker(this.blocker).start(30);

    this.header = new HttpHeaders({'Authorization': 'Bearer ' + this.currentUser.token});

    this.keepAlive.request(new HttpRequest('GET', environment.serverUrl + '/auth/status', this.header));

    // if connection lose, send message for MQTT broker to logout user;
    this.keepAlive.onPingResponse.subscribe(response => {
      if (!isUndefined(response.status) && response.status !== 200) {
        console.log('Connection timed out!' + new Date());
        this.authenticationService.logout(this.currentUser);
        this.router.navigate(['/login']);
        this.idle.stop();
        this.keepAlive.stop();
      }
    });
    this.keepAlive.onPing.subscribe(data => {
      console.log('Keepalive.ping() called!' + new Date());
    });
    this.keepAlive.interval(60);

    this.idle.setIdle(120);
    this.idle.setTimeout(120);
    this.idle.setInterrupts(DEFAULT_INTERRUPTSOURCES);
    // if idle timed out, send message for MQTT broker to logout user;
    this.idle.onTimeout.subscribe(() => {
      console.log('Timed out!' + new Date());
      this.authenticationService.logout(this.currentUser);
      this.router.navigate(['/login']);
      this.idle.stop();
      this.keepAlive.stop();
    });

    idle.watch();

    /**
     * Update the lastRequest for currentUser
     */
    this.userService.updateLastRequest(this.currentUser.id);
  }

  ngOnInit(): void {
    this.doFilter();

    this.usersSubscription = this.mqttService.observe('restaurants/status/').subscribe((data: MqttMessage) => {

      let restaurantOn = JSON.parse(data.payload.toString());

      if (this.source.data.some(r => r.id === restaurantOn.id)) {
        let index2 = this.source.data.findIndex(r => r.id === restaurantOn.id);
        this.source.data.splice(index2, 1);
        this.source.data.push(restaurantOn);
      }
      this.userSort = this.source.data.sort((a, b) => a.minutesOffline - b.minutesOffline);
    });
  }

  ngOnDestroy(): void {
    this.usersSubscription.unsubscribe();
  }

  private doFilter(): void {
    this.source.filter();
  }

  public onScroll() {
    console.log('meu amigo, funcione!');
  }
}
