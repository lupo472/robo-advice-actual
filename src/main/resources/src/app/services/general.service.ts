import { Injectable } from '@angular/core';
import { Http, Response,Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class GeneralService {
// public url:string;
  constructor(public http: Http) {
    // this.url = 'https://reqres.in/api/register';
  }
}
