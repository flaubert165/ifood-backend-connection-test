import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AlertService } from '../../services/alert.service';
import {UserService} from "../../services/user.service";
import {MqttService} from "ngx-mqtt";
import {User} from "../../models/user";

@Component({
  moduleId: module.id,
  templateUrl: 'register.restaurant.component.html'
})

export class RegisterRestaurantComponent {
  currentUser: User;
  model: any = {};
  loading = false;

  constructor(
    private router: Router,
    private userService: UserService,
    private alertService: AlertService,
    private mqttService: MqttService) {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
  }

  register() {
    this.loading = true;
    this.userService.create(this.model)
      .subscribe(
        data => {
          /**
           * Update the lastRequest for currentUser
           */
          this.userService.updateLastRequest(this.currentUser.id);
          this.alertService.success('Registration successful', true);
          this.router.navigate(['']);
        },
        error => {
          this.alertService.error(error._body);
          this.loading = false;
        });
  }
}
