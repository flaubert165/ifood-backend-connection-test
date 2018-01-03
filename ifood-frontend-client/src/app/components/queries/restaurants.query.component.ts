import {Component, OnInit} from "@angular/core";
import {Subscription} from "rxjs/Subscription";
import {MqttMessage, MqttService} from "ngx-mqtt";
import {UserService} from "../../services/user.service";
import {User} from "../../models/user";
import {environment} from "../../../environments/environment";
import {isUndefined} from "util";
import {DEFAULT_INTERRUPTSOURCES} from "@ng-idle/core";
import {HttpHeaders, HttpRequest} from "@angular/common/http";
import {AuthenticationService} from "../../services/authentication.service";
import { Router} from "@angular/router";
import {Idle} from "@ng-idle/core";
import {Keepalive} from "@ng-idle/keepalive";

@Component({
  moduleId: module.id,
  templateUrl: 'restaurants.query.component.html'
})

export class RestaurantsQueryComponent implements OnInit {
  currentUser: User;
  users: User[] = new Array();
  userSort: User[] = new Array();
  public usersSubscription: Subscription;
  header: any;

  constructor(private userService: UserService,
              private mqttService: MqttService,
              private authenticationService: AuthenticationService,
              private router: Router,
              private idle: Idle,
              private keepAlive: Keepalive) {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    this.loadAllUsers();

    this.header = new HttpHeaders({ 'Authorization': 'Bearer ' + this.currentUser.token});

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
    this.keepAlive.onPing.subscribe( data => {
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
  }

  ngOnInit() {

  this.usersSubscription = this.mqttService.observe('restaurants/status/').subscribe((data: MqttMessage) => {

      let restaurantOn = JSON.parse(data.payload.toString());

      if (this.users.some(r => r.id === restaurantOn.id)) {
        let index2 = this.users.findIndex(r => r.id === restaurantOn.id);
        this.users.splice(index2, 1);
        this.users.push(restaurantOn);
      }

      this.userSort = this.users.sort((a, b) => a.minutesOffline - b.minutesOffline);

    });

  }

  private loadAllUsers() {

    this.userService.getAll().subscribe(users => {

      this.users = users;

    });

  }

}
