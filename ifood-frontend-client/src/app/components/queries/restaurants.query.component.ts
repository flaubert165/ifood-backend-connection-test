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

@Component({
  moduleId: module.id,
  templateUrl: 'restaurants.query.component.html'
})

export class RestaurantsQueryComponent implements OnInit {
  private _timer: Observable<Date>;
  public date: Date;
  currentUser: User;
  users: User[] = new Array();
  public availableUsers: any[] = new Array();
  public unavailableUsers: any[] = new Array();
  public availableSubscription: Subscription;
  public unavailableSubscription: Subscription;



  constructor(private userService: UserService, private mqttService: MqttService) {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    this.loadAllUsers();

   /* this.date = new Date(0);
    this._timer = Observable.timer(1, 120000).map(() => {
      this.date.setSeconds(this.date.getSeconds() + 1);
      return this.date;
    });*/
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
