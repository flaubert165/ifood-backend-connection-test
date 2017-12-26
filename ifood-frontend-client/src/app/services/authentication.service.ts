import { Injectable } from '@angular/core';
import {Http, Headers, Response, RequestOptions} from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import {environment} from "../../environments/environment";
import {User} from "../models/user";
import {MqttService} from "ngx-mqtt";
import {Status} from "../models/enums/status";


@Injectable()
export class AuthenticationService {



  constructor(private http: Http, private mqttService: MqttService) {

  }

  login(email: string, password: string) {
    return this.http.post(environment.serverUrl + '/auth/login', { email: email, password: password })
      .map((response: Response) => {
        // login successful if there's a jwt token in the response
        let user = response.json();
        if (user && user.token) {
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          localStorage.setItem('currentUser', JSON.stringify(user));
        }
      });
  }

  logout(user: User) {
    this.mqttService.unsafePublish('restaurants/logout/', user.id.toString(), {qos: 1, retain: true});
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
  }

  // private helper methods
  private jwt() {
    // create authorization header with jwt token
    let currentUser = JSON.parse(localStorage.getItem('currentUser'));
    if (currentUser && currentUser.token) {
      let headers = new Headers({ 'Authorization': 'Bearer ' + currentUser.token });
      return new RequestOptions({ headers: headers });
    }
  }
}
