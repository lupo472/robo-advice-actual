import { Injectable, Inject } from '@angular/core';
import { AppConfig } from './app.config';
import { Cookie } from 'ng2-cookies';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';
import {GenericResponse} from "../model/generic-response";

@Injectable()
export class AppService {

  headers: Headers;
  opts: RequestOptions;

  constructor(private http: Http) {
    // this.headers = new Headers();
    // this.headers.append('Authorization',Cookie.get('token'));
    // this.opts = new RequestOptions();
    // opts.headers = headers;
  }

  private logError(error: any) {
    console.error(error.error);
    throw error;
  }

  loginUser(user) {
    return this.http.post(AppConfig.url + 'loginUser',user)
      .map(response => response.json());
  }

  registerUser(user) {
    return this.http.post(AppConfig.url + 'registerUser', user)
      .map(response => response.json());
  }

  getDefaultStrategySet() {
    this.headers = new Headers();
    this.headers.append('Authorization',Cookie.get('token'));
    //this.headers.append('Access-Control-Allow-Credentials','true');
    //this.headers.append('Content-Type','application/json;charset=UTF-8');
    this.opts = new RequestOptions();
    this.opts.headers = this.headers;
    return this.http.post(AppConfig.url + 'getDefaultStrategySet', {},this.opts)
      .map(response => {
        const json = response.json();
        if (response.ok) {
          return json.data;
        } else {
          return this.logError(json);
        }
      });
  }

  getAssetClassSet() {
    this.headers = new Headers();
    this.headers.append('Authorization',Cookie.get('token'));
    //this.headers.append('Access-Control-Allow-Credentials','true');
    //this.headers.append('Content-Type','application/json;charset=UTF-8');
    this.opts = new RequestOptions();
    this.opts.headers = this.headers;
    return this.http.post(AppConfig.url + 'getAssetClassSet', {},this.opts)
      .map(response => {
        const json = response.json();
        if (response.ok) {
          return json.data;
        } else {
          return this.logError(json);
        }
      });
  }

  getFinancialDataSet(period) {
    this.headers = new Headers();
    this.headers.append('Authorization',Cookie.get('token'));
    this.opts = new RequestOptions();
    this.opts.headers = this.headers;
    return this.http.post(AppConfig.url + 'getFinancialDataSet', { period: period },this.opts)
      .map(response => response.json());
  }

  getPortfolioForPeriod(period) {
    this.headers = new Headers();
    this.headers.append('Authorization',Cookie.get('token'));
    //this.headers.append('Access-Control-Allow-Credentials','true');
    //this.headers.append('Content-Type','application/json;charset=UTF-8');
    this.opts = new RequestOptions();
    this.opts.headers = this.headers;
    return this.http.post(AppConfig.url + 'getPortfolioForPeriod', { period: period },this.opts)
      .map(response => response.json());
  }

  addCapital(amount) {
    this.headers = new Headers();
    this.headers.append('Authorization',Cookie.get('token'));
    //this.headers.append('Access-Control-Allow-Credentials','true');
    //this.headers.append('Content-Type','application/json;charset=UTF-8');
    this.opts = new RequestOptions();
    this.opts.headers = this.headers;
    return this.http.post(AppConfig.url + 'addCapital', { amount: amount },this.opts)
      .map(response => {console.log(response.json())});
  }

  getCurrentCapital() {
    this.headers = new Headers();
    this.headers.append('Authorization',Cookie.get('token'));
    //this.headers.append('Access-Control-Allow-Credentials','true');
    //this.headers.append('Content-Type','application/json;charset=UTF-8');
    this.opts = new RequestOptions();
    this.opts.headers = this.headers;
    return this.http.post(AppConfig.url + 'getCurrentCapital', {}, this.opts)
      .map(response => response.json());
  }

  getCapitalForPeriod(period) {
    this.headers = new Headers();
    this.headers.append('Authorization',Cookie.get('token'));
    //this.headers.append('Access-Control-Allow-Credentials','true');
    //this.headers.append('Content-Type','application/json;charset=UTF-8');
    this.opts = new RequestOptions();
    this.opts.headers = this.headers;
    return this.http.post(AppConfig.url + 'getCapitalForPeriod', { period: period },this.opts)
      .map(response => response.json());
  }

  setCustomStrategy(strategy) {
    this.headers = new Headers();
    this.headers.append('Authorization',Cookie.get('token'));
    //this.headers.append('Access-Control-Allow-Credentials','true');
    //this.headers.append('Content-Type','application/json;charset=UTF-8');
    this.opts = new RequestOptions();
    this.opts.headers = this.headers;
    return this.http.post(AppConfig.url + 'setCustomStrategy', strategy,this.opts)
      .map(response => response.json());
  }

  getActiveStrategy() {
    this.headers = new Headers();
    this.headers.append('Authorization',Cookie.get('token'));
    //this.headers.append('Access-Control-Allow-Credentials','true');
    //this.headers.append('Content-Type','application/json;charset=UTF-8');
    this.opts = new RequestOptions();
    this.opts.headers = this.headers;
    return this.http.post(AppConfig.url + 'getActiveStrategy', {}, this.opts)
      .map(response => response.json());
  }

  getHistoryStrategies() {
    this.headers = new Headers();
    this.headers.append('Authorization',Cookie.get('token'));
    //this.headers.append('Access-Control-Allow-Credentials','true');
    //this.headers.append('Content-Type','application/json;charset=UTF-8');
    this.opts = new RequestOptions();
    this.opts.headers = this.headers;
    return this.http.post(AppConfig.url + 'getCustomStrategyHistory', {Period:'0'}, this.opts)
        .map(response => response.json());
  }

}
