import {Component, OnInit} from "@angular/core";
import {Subscription} from "rxjs/Subscription";
import {MqttMessage, MqttService} from "ngx-mqtt";
import {UserService} from "../../services/user.service";
import {User} from "../../models/user";
import {Status} from "../../models/enums/status";
import {Observable} from "rxjs/Observable";
import {Http} from "@angular/http";
import {environment} from "../../../environments/environment";
import {Scheduler} from "rxjs/Scheduler";
import {isUndefined} from "util";
import {DEFAULT_INTERRUPTSOURCES} from "@ng-idle/core";
import {HttpHeaders, HttpRequest} from "@angular/common/http";
import {AuthenticationService} from "../../services/authentication.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Idle} from "@ng-idle/core";
import {Keepalive} from "@ng-idle/keepalive";

@Component({
  moduleId: module.id,
  templateUrl: 'restaurants.query.component.html'
})

export class RestaurantsQueryComponent implements OnInit {
  currentUser: User;
  users: User[] = new Array();
  public availableUsers: any[] = new Array();
  public unavailableUsers: any[] = new Array();
  public availableSubscription: Subscription;
  public unavailableSubscription: Subscription;
  header: any;


  constructor(private userService: UserService,
              private mqttService: MqttService,
              private authenticationService: AuthenticationService,
              private http: Http,
              private router: Router,
              private idle: Idle,
              private keepAlive: Keepalive,
              private route: ActivatedRoute) {
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
    this.keepAlive.interval(10);

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

  this.availableSubscription = this.mqttService.observe('restaurants/online/').subscribe((data: MqttMessage) => {

      let restaurantOn = JSON.parse(data.payload.toString());

      if (this.unavailableUsers.some(r => r.id === restaurantOn.id)) {
        let index = this.unavailableUsers.findIndex(r => r.id === restaurantOn.id);
        this.unavailableUsers.splice(index, 1);
        this.availableUsers.push(restaurantOn);
      } else {
        let index = this.availableUsers.findIndex(r => r.id === restaurantOn.id);
        this.availableUsers.splice(index, 1);
        this.availableUsers.push(restaurantOn);

        let index2 = this.users.findIndex(r => r.id === restaurantOn.id);
        this.users.splice(index2, 1);
        this.users.push(restaurantOn);
      }

    });

    this.unavailableSubscription = this.mqttService.observe('restaurants/offline/').subscribe((data: MqttMessage) => {

      let restaurantOff = JSON.parse(data.payload.toString());

      if (!this.availableUsers.some(r => r.id === restaurantOff.id) && restaurantOff.status === 'UnavailableOffline') {
        let index = this.availableUsers.findIndex(r => r.id === restaurantOff.id);
        this.availableUsers.splice(index, 1);
        this.unavailableUsers.push(restaurantOff);
      } else if (this.availableUsers.some(r => r.id === restaurantOff.id) && restaurantOff.status === 'AvailableOffline') {
        let index = this.availableUsers.findIndex(r => r.id === restaurantOff.id);
        this.availableUsers.splice(index, 1);
        this.availableUsers.push(restaurantOff);

        let index2 = this.users.findIndex(r => r.id === restaurantOff.id);
        this.users.splice(index2, 1);
        this.users.push(restaurantOff);
      }

    });

  }

  private loadAllUsers() {

    this.userService.getAll().subscribe(users => {

      this.users = users;

     for (let user of users){
        if (user.status === 'AvailableOnline' || user.status === 'AvailableOffline') {
          this.availableUsers.push(user);
        } else  {
          this.unavailableUsers.push(user);
        }
      }

    });

  }

}
