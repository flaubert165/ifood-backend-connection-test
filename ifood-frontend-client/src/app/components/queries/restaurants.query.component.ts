import {Component, OnInit} from "@angular/core";
import {Subscription} from "rxjs/Subscription";
import {MqttMessage, MqttService} from "ngx-mqtt";
import {UserService} from "../../services/user.service";
import {User} from "../../models/user";
import {isUndefined} from "util";
import {current} from "codelyzer/util/syntaxKind";

@Component({
  moduleId: module.id,
  templateUrl: 'restaurants.query.component.html'
})

export class RestaurantsQueryComponent implements OnInit {

  currentUser: User;
  users: User[] = [];
  public availableSubscription: Subscription;
  public unavailableSubscription: Subscription;
  public availableUsers: any[] = new Array();
  public unavailableUsers: any[] = new Array();


  constructor(private userService: UserService, private mqttService: MqttService){
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
  }

  ngOnInit() {
    this.loadAllUsers();

    this.availableSubscription = this.mqttService.observe('restaurants/online/').subscribe((data: MqttMessage) => {

      let restaurant = JSON.parse(data.payload.toString());

      if (!this.unavailableUsers.some(r => r.id === restaurant.id) ||
          !this.availableUsers.some(r => r.id === restaurant.id)) {
        this.availableUsers.push(restaurant);
      }else{
        let index = this.unavailableUsers.findIndex(r => r.id === restaurant.id);
        this.unavailableUsers.splice(index, 1);
        this.availableUsers.push(restaurant);
      }

    });

    this.unavailableSubscription = this.mqttService.observe('restaurants/offline/').subscribe((data: MqttMessage) => {
      let restaurant = JSON.parse(data.payload.toString());

      if(!this.availableUsers.some(r => r.id === restaurant.id) && restaurant.status === 'UnavailableOffline') {
        this.unavailableUsers.push(restaurant);
      }else{
        let index = this.availableUsers.findIndex(r => r.id === restaurant.id && r.status === 'UnavailableOffline');
        this.availableUsers.splice(index, 1);
        this.unavailableUsers.push(restaurant);
      }
    });
  }

  private loadAllUsers() {
    this.userService.getAll().subscribe(users => {
      this.users = users;

      for(let item of this.users){

        if (!this.unavailableUsers.some(r => r.id === item.id) ||
            !this.availableUsers.some(r => r.id === item.id)) {
          this.availableUsers.push(item);
        }else{
          let index = this.unavailableUsers.findIndex(r => r.id === item.id);
          this.unavailableUsers.splice(index, 1);
          this.availableUsers.push(item);
        }

      }

    });
  }

}
