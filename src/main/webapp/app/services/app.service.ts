import { Injectable, Inject } from '@angular/core';
import { AppConfig } from './app.config';
import { Cookie } from 'ng2-cookies';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

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
      .map(response => response.json());
  }

  getAssetClassSet() {
    this.headers = new Headers();
    this.headers.append('Authorization',Cookie.get('token'));
    //this.headers.append('Access-Control-Allow-Credentials','true');
    //this.headers.append('Content-Type','application/json;charset=UTF-8');
    this.opts = new RequestOptions();
    this.opts.headers = this.headers;
    return this.http.post(AppConfig.url + 'getAssetClassSet', {},this.opts)
      .map(response => response.json());
  }

  getFinancialData(id, period) {
    return this.http.post(AppConfig.url + 'getFinancialData', { id: id, period: period })
      .map(response => response.json());
  }

  getUserCurrentPortfolio(id, email, password) {
    this.headers = new Headers();
    this.headers.append('Authorization',Cookie.get('token'));
    //this.headers.append('Access-Control-Allow-Credentials','true');
    //this.headers.append('Content-Type','application/json;charset=UTF-8');
    this.opts = new RequestOptions();
    this.opts.headers = this.headers;
    return this.http.post(AppConfig.url + 'getUserCurrentPortfolio', { id: id, email: email, password: password },this.opts)
      .map(response => response.json());
  }

  getUserPortfolioPeriod(id, period) {
    this.headers = new Headers();
    this.headers.append('Authorization',Cookie.get('token'));
    //this.headers.append('Access-Control-Allow-Credentials','true');
    //this.headers.append('Content-Type','application/json;charset=UTF-8');
    this.opts = new RequestOptions();
    this.opts.headers = this.headers;
    return this.http.post(AppConfig.url + 'getUserPortfolioPeriod', { id: id, period: period },this.opts)
      .map(response => response.json());
  }

  addCapital(id, amount) {
    this.headers = new Headers();
    this.headers.append('Authorization',Cookie.get('token'));
    //this.headers.append('Access-Control-Allow-Credentials','true');
    //this.headers.append('Content-Type','application/json;charset=UTF-8');
    this.opts = new RequestOptions();
    this.opts.headers = this.headers;
    return this.http.post(AppConfig.url + 'addCapital', { idUser: id, amount: amount },this.opts)
      .map(response => response.json());
  }

  getCurrentCapital(user,token) {
    this.headers = new Headers();
    this.headers.append('Authorization',token);
    //this.headers.append('Access-Control-Allow-Credentials','true');
    //this.headers.append('Content-Type','application/json;charset=UTF-8');
    this.opts = new RequestOptions();
    this.opts.headers = this.headers;
    return this.http.post(AppConfig.url + 'getCurrentCapital',user,this.opts)
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

  getActiveStrategy(user,token) {
    this.headers = new Headers();
    this.headers.append('Authorization',token);
    //this.headers.append('Access-Control-Allow-Credentials','true');
    //this.headers.append('Content-Type','application/json;charset=UTF-8');
    this.opts = new RequestOptions();
    this.opts.headers = this.headers;
    return this.http.post(AppConfig.url + 'getActiveUserCustomStrategy', user, this.opts)
      .map(response => response.json());
  }
  authenticateUser(id, token) {
    return this.http.post(AppConfig.url + 'authenticate', { id: id, token: token })
      .map(response => response.json());
  }
}
