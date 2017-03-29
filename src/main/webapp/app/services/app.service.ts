import { Injectable, Inject } from '@angular/core';
import { AppConfig } from './app.config';
import { Cookie } from 'ng2-cookies';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {GenericResponse} from "../model/generic-response";
import {AssetClass} from "../model/asset-class";
import {FinancialData} from "../model/financial-data";
import {Strategy} from "../model/strategy";
import {DefaultStrategy} from "../model/default-strategy";
import {IDefaultStrategy} from "../model/interfaces/idefault-strategy";
import {IAssetClass} from "../model/interfaces/iasset-class";


@Injectable()
export class AppService {

  headers: Headers;
  opts: RequestOptions;

  constructor(private http: Http) {
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

  getDefaultStrategySet() : Observable<IDefaultStrategy[]> {
    this.headers = new Headers();
    this.headers.append('Authorization',Cookie.get('token'));
    this.opts = new RequestOptions();
    this.opts.headers = this.headers;
    return this.http.post(AppConfig.url + 'getDefaultStrategySet', {},this.opts)
        .map(this.extractData)
        .catch(this.handleError);
  }

  getAssetClassSet() : Observable<IAssetClass[]>{
    this.headers = new Headers();
    this.headers.append('Authorization',Cookie.get('token'));
    this.opts = new RequestOptions();
    this.opts.headers = this.headers;
    return this.http.post(AppConfig.url + 'getAssetClassSet', {},this.opts)
        .map(this.extractData)
        .catch(this.handleError);
  }

  private extractData(res : Response) {
    let body = res.json();
    return body.data;
  }

  private handleError(error:Response | any) {
    let errMsg:string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    //console.error(errMsg);
    return Observable.throw(errMsg);
  }

  getFinancialDataSet(period) : Observable<FinancialData[]> {
    this.headers = new Headers();
    this.headers.append('Authorization',Cookie.get('token'));
    this.opts = new RequestOptions();
    this.opts.headers = this.headers;
    return this.http.post(AppConfig.url + 'getFinancialDataSet', { period: period },this.opts)
        .map(this.extractData)
        .catch(this.handleError);
  }
  getForecast(period): Observable<FinancialData[]> {
    this.headers = new Headers();
    this.headers.append('Authorization',Cookie.get('token'));
    this.opts = new RequestOptions();
    this.opts.headers = this.headers;
    return this.http.post(AppConfig.url + 'getForecast', { period: period },this.opts)
        .map(this.extractData)
        .catch(this.handleError);
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

  getDemo(period) {
    this.headers = new Headers();
    this.headers.append('Authorization',Cookie.get('token'));
    //this.headers.append('Access-Control-Allow-Credentials','true');
    //this.headers.append('Content-Type','application/json;charset=UTF-8');
    this.opts = new RequestOptions();
    this.opts.headers = this.headers;
    return this.http.post(AppConfig.url + 'getDemo', { period: period },this.opts)
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

  getBacktesting(list, period, capital) {
    this.headers = new Headers();
    this.headers.append('Authorization',Cookie.get('token'));
    //this.headers.append('Access-Control-Allow-Credentials','true');
    //this.headers.append('Content-Type','application/json;charset=UTF-8');
    this.opts = new RequestOptions();
    this.opts.headers = this.headers;
    return this.http.post(AppConfig.url + 'getBacktesting', {list: list, period: period, capital: capital}, this.opts)
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
    this.opts = new RequestOptions();
    this.opts.headers = this.headers;
    return this.http.post(AppConfig.url + 'setCustomStrategy', strategy ,this.opts)
      .map(this.extractData)
        .catch(this.handleError);
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
  getAdvice(period) {
    this.headers = new Headers();
    this.headers.append('Authorization',Cookie.get('token'));
    //this.headers.append('Access-Control-Allow-Credentials','true');
    //this.headers.append('Content-Type','application/json;charset=UTF-8');
    this.opts = new RequestOptions();
    this.opts.headers = this.headers;
    return this.http.post(AppConfig.url + 'getAdvice', {period:period}, this.opts)
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
