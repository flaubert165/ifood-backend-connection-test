import {Component, OnDestroy, OnInit} from '@angular/core';
import { UserService } from '../services/user.service';
import { User } from '../models/user';
import {AuthenticationService} from "../services/authentication.service";
import {Http} from "@angular/http";
import {ActivatedRoute, Router} from "@angular/router";
import {DEFAULT_INTERRUPTSOURCES, Idle} from "@ng-idle/core";
import {Keepalive} from "@ng-idle/keepalive";
import {MqttService} from "ngx-mqtt";

@Component({
  moduleId: module.id,
  templateUrl: 'home.component.html'
})

export class HomeComponent implements OnInit, OnDestroy {
  currentUser: User;

  constructor(private authenticationService: AuthenticationService,
              private http: Http,
              private router: Router,
              private idle: Idle,
              private keepAlive: Keepalive,
              private route: ActivatedRoute) {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));

    this.keepAlive.onPing.subscribe( data => {
      console.log('Keepalive.ping() called!' + new Date());
    });

    this.keepAlive.interval(5);
    this.keepAlive.request('auth/status');
    this.idle.setIdle(10);
    this.idle.setTimeout(10);
    this.idle.setInterrupts(DEFAULT_INTERRUPTSOURCES);

    this.keepAlive.onPingResponse.subscribe(response => {
      console.log(response.status.toString());
    });

    this.idle.onTimeout.subscribe(() => {
      console.log('Timed out!' + new Date());
      this.authenticationService.logout(this.currentUser);
      this.router.navigate(['/login']);
    });

    idle.watch();
  }

  ngOnInit() {

  }

  ngOnDestroy(): void {

  }
}
