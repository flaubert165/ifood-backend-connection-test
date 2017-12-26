import {Component, OnInit} from "@angular/core";
import {Subscription} from "rxjs/Subscription";
import {MqttMessage, MqttService} from "ngx-mqtt";
import {UserService} from "../../services/user.service";
import {User} from "../../models/user";
import {Status} from "../../models/enums/status";

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



  constructor(private userService: UserService, private mqttService: MqttService){
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
  }

  ngOnInit() {
    this.loadAllUsers();

    this.availableSubscription = this.mqttService.observe('restaurants/online/').subscribe((data: MqttMessage) => {

      let restaurant = JSON.parse(data.payload.toString());


     /* if (this.unavailableUsers.some(r => r.id === restaurant.id) &&
        (restaurant.status === Status.AvailableOnline || restaurant.status === Status.UnavailableOffline)) {
        let index = this.unavailableUsers.findIndex(r => r.id === restaurant.id);
        this.unavailableUsers.splice(index, 1);
        this.availableUsers.push(restaurant);
      }else{
        this.availableUsers.push(restaurant);
      }*/

      /*if ((!this.unavailableUsers.some(r => r.id === restaurant.id) &&
          !this.availableUsers.some(r => r.id === restaurant.id)) &&
          (restaurant.status === Status.AvailableOnline || restaurant.status === Status.AvailableOffline)) {
        this.availableUsers.push(restaurant);
      }else{
        let index = this.unavailableUsers.findIndex(r => r.id === restaurant.id);
        this.unavailableUsers.splice(index, 1);
        this.availableUsers.push(restaurant);
      }*/

    });

    this.unavailableSubscription = this.mqttService.observe('restaurants/offline/').subscribe((data: MqttMessage) => {

      let restaurant = JSON.parse(data.payload.toString());

      /*if (){

      }*/

      /*if(!this.availableUsers.some(r => r.id === restaurant.id) && restaurant.status === Status.UnavailableOffline) {
        this.unavailableUsers.push(restaurant);
      }else{
        let index = this.availableUsers.findIndex(r => r.id === restaurant.id && r.status === Status.UnavailableOffline);
        this.availableUsers.splice(index, 1);
        this.unavailableUsers.push(restaurant);
      }*/

    });

  }

  private loadAllUsers() {

    this.userService.getAll().subscribe(users => {

      this.users = users;

     /* for(let user of users){

        if (this.unavailableUsers.some(r => r.id === user.id) &&
          (user.status === Status.AvailableOnline || user.status === Status.UnavailableOffline)) {
          let index = this.unavailableUsers.findIndex(r => r.id === user.id);
          this.unavailableUsers.splice(index, 1);
          this.availableUsers.push(user);
        }else{
          this.availableUsers.push(user);
        }

      }*/

    });

  }

}
