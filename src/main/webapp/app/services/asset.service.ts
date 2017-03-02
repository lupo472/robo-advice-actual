import { Injectable, Inject } from '@angular/core';
import { AppConfig } from './app.config';

import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class AssetService {
  
  result: Object;

  constructor(private http:Http) { }

  getAssetClassSet() {
    return this.http.post(AppConfig.url + 'getAssetClassSet', {})
      .map(response => response.json());
  }


  getAssetSet(){
    return this.http.post(AppConfig.url + 'getAssetSet', {})
      .map(response => response.json());
  }
}
