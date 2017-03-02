
import { Injectable, Inject } from '@angular/core';
import { AppConfig } from './app.config';

import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class UserService {

  constructor(private http:Http) { }

  login(user) {
    return this.http.post(AppConfig.url + 'loginUsersss', user)
      .map(response => response.json());
  }


  register(user){
    return this.http.post(AppConfig.url + 'registerUser', user)
      .map(response => response.json());
  }
}
