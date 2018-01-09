import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
import { User } from '../models/user';
import {environment} from "../../environments/environment";
import {MqttService} from "ngx-mqtt";

@Injectable()
export class UserService {
  constructor(private http: Http, private mqttService: MqttService) { }

  getAll() {
    return this.http.get(environment.serverUrl + '/user', this.jwt()).map((response: Response) => response.json());
  }

  getById(id: string) {
    return this.http.get(environment.serverUrl + '/user/' + id, this.jwt()).map((response: Response) => response.json());
  }

  create(user: User) {
    return this.http.post(environment.serverUrl + '/user/register', user, this.jwt());
  }

  update(user: User) {
    return this.http.put(environment.serverUrl + '/user/' + user.id, user, this.jwt());
  }

  delete(id: string) {
    return this.http.delete(environment.serverUrl + '/user/' + id, this.jwt());
  }

  updateLastRequest(id: number){
    /**
     * Update the lastRequest for currentUser
     */
    this.mqttService.unsafePublish('restaurants/updateLasRequest/', id.toString(), {qos: 1, retain: true});
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
