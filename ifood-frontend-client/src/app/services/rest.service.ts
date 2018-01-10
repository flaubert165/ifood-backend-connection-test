import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, URLSearchParams } from '@angular/http';
import {environment} from "../../environments/environment";
import {TranslateService} from "@ngx-translate/core";
import {ResponseMap} from "../response.map";
import {UserService} from "./user.service";

@Injectable()
export class RestService {

    constructor(private http: Http, private translate: TranslateService, private userService: UserService) {
    }

    private resolve(path: string): string {
        return environment.serverUrl + '/' + path;
    }

    private createRequestOptions(urlParams?: any, isWithoutAuthorization?: boolean): RequestOptions {
        let headers = new Headers({ 'Content-Type': 'application/json' });
        headers.append('Authorization', this.jwt());
        let requestOptions = new RequestOptions({ headers: headers });


        if(urlParams && Object.keys(urlParams).length > 0) {
            let urlSearchParams = new URLSearchParams();

            for (let key in urlParams) {
                urlSearchParams.append(key, urlParams[key]);
            }

            requestOptions.params = urlSearchParams;
        }

        return requestOptions;
    }

    public get<T>(url: string, queryString?: any, isWithoutAuthorization?: boolean, external?: boolean): ResponseMap<T> {
        return new ResponseMap<T>(this.translate, this.http.get(external ? url : this.resolve(url),
          this.createRequestOptions(queryString, isWithoutAuthorization)));
    }

    public delete<T>(url: string, queryString?: any, isWithoutAuthorization?: boolean, external?: boolean): ResponseMap<T> {
        return new ResponseMap<T>(this.translate, this.http.delete(external ? url : this.resolve(url),
          this.createRequestOptions(queryString, isWithoutAuthorization)));
    }

    public post<T>(url: string, data: any, queryString?: any, isWithoutAuthorization?: boolean, external?: boolean): ResponseMap<T> {
        return new ResponseMap<T>(this.translate, this.http.post(external ? url : this.resolve(url),
          JSON.stringify(data), this.createRequestOptions(queryString, isWithoutAuthorization)));
    }

    public put<T>(url: string, data: any, queryString?: any, isWithoutAuthorization?: boolean, external?: boolean): ResponseMap<T> {
        return new ResponseMap<T>(this.translate, this.http.put(external ? url : this.resolve(url),
          JSON.stringify(data), this.createRequestOptions(queryString, isWithoutAuthorization)));
    }

  private jwt() {
    // create authorization header with jwt token
    let currentUser = JSON.parse(localStorage.getItem('currentUser'));
    if (currentUser && currentUser.token) {
      return  'Bearer ' + currentUser.token;
    }
  }
}
