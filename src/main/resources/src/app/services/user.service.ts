import { Injectable } from '@angular/core';
import { GeneralService } from './general.service';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class UserService {
  constructor(private http:Http) {}
loginUser() {
    return this.http.post('https://reqres.in/api/register',{"email":"sydney@fife","password":"pistol"})
      .map(response => response.json());
}
}
