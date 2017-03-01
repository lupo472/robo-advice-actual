import { Injectable } from '@angular/core';
import { GeneralService } from './general.service';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class UserService {
  
  public url:GeneralService;
  
  constructor(private http:Http, public GeneralUrl: GeneralService) {
    this.url = GeneralUrl;
  }

  login() {
    return this.http.post(this.url + 'login',{"email": "peter@klaven","password": "cityslicka"})
      .map(response => response.json());
  }
  
  register(){
    return this.http.post(this.url + 'register',{"email":"sydney@fife","password":"pistol"})
      .map(response => response.json());
  }
}
