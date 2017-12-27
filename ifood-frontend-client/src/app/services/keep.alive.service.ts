import {Injectable} from "@angular/core";
import {AuthenticationService} from "./authentication.service";
import {Http} from "@angular/http";
import {ActivatedRoute, Router} from "@angular/router";
import {Idle} from "@ng-idle/core";
import {Keepalive} from "@ng-idle/keepalive";
import {HttpHeaders, HttpRequest} from "@angular/common/http";
import {User} from "../models/user";
import {environment} from "../../environments/environment";
import {DEFAULT_INTERRUPTSOURCES} from "@ng-idle/core";
import {isUndefined} from "util";

@Injectable()
export class KeepAliveService {
  currentUser: User;
  header: any;

  constructor(private authenticationService: AuthenticationService,
              private http: Http,
              private router: Router,
              private idle: Idle,
              private keepAlive: Keepalive,
              private route: ActivatedRoute) {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    this.header = new HttpHeaders({ 'Authorization': 'Bearer ' + this.currentUser.token});

    this.keepAlive.request(new HttpRequest('GET', environment.serverUrl + '/auth/status', this.header));
    this.keepAlive.onPing.subscribe( data => {
      console.log('Keepalive.ping() called!' + new Date());
    });

    this.keepAlive.interval(5);
    this.idle.setIdle(5);
    this.idle.setTimeout(5);
    this.idle.setInterrupts(DEFAULT_INTERRUPTSOURCES);

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
}
