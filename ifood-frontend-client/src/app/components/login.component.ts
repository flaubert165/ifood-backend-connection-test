import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AlertService } from '../services/alert.service';
import { AuthenticationService } from '../services/authentication.service';
import {User} from "../models/user";
import {MqttService} from "ngx-mqtt";

@Component({
  moduleId: module.id,
  templateUrl: 'login.component.html'
})

export class LoginComponent implements OnInit {
  model: any = {};
  loading = false;
  returnUrl: string;
  currentUser: User;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService,
    private alertService: AlertService,
    private mqttService: MqttService) {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
  }

  ngOnInit() {
    // reset login status
    this.authenticationService.logout(this.currentUser);

    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  login() {
    this.loading = true;
    this.authenticationService.login(this.model.email, this.model.password)
      .subscribe(
        data => {
          /**
           * Update the lastRequest for currentUser
           */
          this.authenticationService.updateLastRequest(this.currentUser.id);
          this.alertService.success('Login successful', true);
          this.router.navigate([this.returnUrl]);
        },
        error => {
          this.alertService.error(error._body);
          this.loading = false;
        });
  }


}
