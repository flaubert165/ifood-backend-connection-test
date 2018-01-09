import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
import { User } from '../models/user';
import {environment} from "../../environments/environment";
import {UnavalabilitySchedule} from "../models/unavalabilitySchedule";
import {Observable} from "rxjs/Observable";
import {MqttService} from "ngx-mqtt";

@Injectable()
export class UnavailabilityScheduleService {
  constructor(private http: Http, private mqttService: MqttService) {
  }

  getAll() {
    return this.http.get(environment.serverUrl + '/scheduling', this.jwt()).map((response: Response) => response.json());
  }

  getById(id: string) {
    return this.http.get(environment.serverUrl + '/scheduling/' + id, this.jwt()).map((response: Response) => response.json());
  }

  getByUserId(userId: number) {
    return this.http.get(environment.serverUrl + '/scheduling/' + userId.toString(), this.jwt())
      .map((response: Response) => response.json());
  }

  create(obj: UnavalabilitySchedule) {
    return this.http.post(environment.serverUrl + '/scheduling/register', obj, this.jwt());
  }

  update(obj: UnavalabilitySchedule) {
    return this.http.put(environment.serverUrl + '/scheduling/' + obj.id, obj, this.jwt());
  }

  delete(id: number) {
    return this.http.delete(environment.serverUrl + '/scheduling/' + id.toString(), this.jwt());
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
