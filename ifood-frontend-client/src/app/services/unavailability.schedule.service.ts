import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
import { User } from '../models/user';
import {environment} from "../../environments/environment";
import {UnavalabilitySchedule} from "../models/unavalabilitySchedule";

@Injectable()
export class UnavailabilityScheduleService {
  constructor(private http: Http) { }

  getAll() {
    return this.http.get(environment.serverUrl + '/scheduling', this.jwt()).map((response: Response) => response.json());
  }

  getById(id: string) {
    return this.http.get(environment.serverUrl + '/scheduling/' + id, this.jwt()).map((response: Response) => response.json());
  }

  getByUserId(userId: number) {
    return this.http.get(environment.serverUrl + '/scheduling/' + userId.toString(), this.jwt()).map((response: Response) => response.json());
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
