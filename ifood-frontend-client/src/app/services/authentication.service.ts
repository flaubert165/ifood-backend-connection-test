import { Injectable } from '@angular/core';
import {Http, Headers, Response, RequestOptions} from '@angular/http';
import 'rxjs/add/operator/map';
import {environment} from "../../environments/environment";
import {User} from "../models/user";
import {MqttService} from "ngx-mqtt";
import {isUndefined} from "util";


@Injectable()
export class AuthenticationService {
  constructor(private http: Http, private mqttService: MqttService) { }

  login(email: string, password: string) {
    return this.http.post(environment.serverUrl + '/auth/login', { email: email, password: password })
      .map((response: Response) => {
        // login successful if there's a jwt token in the response
        let user = response.json();
        if (user && user.token) {
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          localStorage.setItem('currentUser', JSON.stringify(user));
          /**
           * Update the lastRequest for currentUser
           */
          this.updateLastRequest(user.id);
        }
      });
  }

  logout(user: User) {
    if(user != null && !isUndefined(user)){
      this.mqttService.unsafePublish('restaurants/logout/', user.id.toString(), {qos: 1, retain: true});
      this.updateLastRequest(user.id);
    }
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
  }

  updateLastRequest(id: number){
    /**
     * Update the lastRequest for currentUser
     */
    this.mqttService.unsafePublish('restaurants/updateLasRequest/', id.toString(), {qos: 1, retain: true});
  }
}
