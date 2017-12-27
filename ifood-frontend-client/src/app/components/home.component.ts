import {Component, OnDestroy, OnInit} from '@angular/core';
import { User } from '../models/user';
import {AuthenticationService} from "../services/authentication.service";
import {Http, RequestMethod} from "@angular/http";
import {ActivatedRoute, Router} from "@angular/router";
import {DEFAULT_INTERRUPTSOURCES, Idle} from "@ng-idle/core";
import {Keepalive} from "@ng-idle/keepalive";
import {HttpHeaders, HttpRequest} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {isUndefined} from "util";

@Component({
  moduleId: module.id,
  templateUrl: 'home.component.html'
})

export class HomeComponent implements OnInit, OnDestroy {
  currentUser: User;
  header: any;

  constructor(private authenticationService: AuthenticationService,
              private router: Router,
              private idle: Idle,
              private keepAlive: Keepalive) {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
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

  }

  ngOnDestroy(): void {

  }
}
