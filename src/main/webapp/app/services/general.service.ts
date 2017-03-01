import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class GeneralService {

  constructor() { }
  
  public url = 'https://reqres.in/api/';
}
