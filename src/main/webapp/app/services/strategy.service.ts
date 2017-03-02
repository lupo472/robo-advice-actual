
import { Injectable, Inject } from '@angular/core';
import { AppConfig } from './app.config';

import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class StrategyService {

  constructor(private http:Http) { }

  getDefaultStrategySet() {
    return this.http.post(AppConfig.url + 'getDefaultStrategySet', {})
      .map(response => response.json());
  }
}
