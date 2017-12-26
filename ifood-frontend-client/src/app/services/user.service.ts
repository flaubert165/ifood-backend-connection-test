import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
import { User } from '../models/user';
import {environment} from "../../environments/environment";

@Injectable()
export class UserService {
  constructor(private http: Http) { }

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
