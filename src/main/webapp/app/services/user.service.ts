
import { Injectable, Inject } from '@angular/core';
import { AppConfig } from './app.config';
import { AppService } from './app.service';
import { Cookie } from 'ng2-cookies';
import { User } from '../model/user';
import { Login } from '../model/login';

import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class UserService {

  private capital = 10000;

  private user: User;
  private login:Login;
  private capitalHistory;

  constructor(private AppService: AppService) { }

  //SET AND GET USER
  setLogin(res) {
    this.login = new Login(res.login);
    console.log(res);
    //this.AppService.getCurrentCapital(res.user,res.token).subscribe(res => this.setCapital(res));
    //this.AppService.getActiveStrategy(res.user,res.token).subscribe(res => this.setStrategy(res));

    return res = { response: 1, data: res };
  }

  getLogin() {
    return this.login;
  }

  //AUTH
  authenticate() {
    var token;
    if (Cookie.check("token")) {
      token = Cookie.get("token");
    } else {
      token = "";
    }

    //return this.AppService.authenticateUser(token).map(res => this.printAuth(res));
  }

  //LOGIN
  loginUser(user) {
    return this.AppService.loginUser(user).map(res => { if (res.response == 1) { return this.setLogin({ login: res.data }) } else { return res } });
  }

  //REGISTER
  registerUser(user) {
    return this.AppService.registerUser(user).map(res => { if (res.response == 1) { return this.setLogin({ login: res.data }) } else { return res } });
  }

  //INITIAL REGISTER CAPITAL
  // addCapital() {
  //   return this.AppService.addCapital(this.capital);
  // }

  //SET THE CURRENT CAPITAL FOR THIS USER
  // setCapital(res) {
  //   this.user.capital = res.data.amount;
  // }
  //
  // getCapitalHistory() {
  //   return this.capitalHistory;
  // }
  // printAuth(res) {
  //   console.log("Sessione corretta --> response:", res.response);
  //   return res;
  // }

  //SET THE ACTIVE STRATEGY FOR THIS USER
  // setStrategy(res) {
  //   if (res.response == 1) {
  //     this.user.strategy = res.data.list;
  //   }
  // }
}
