
import { Injectable, Inject } from '@angular/core';
import { AppConfig } from './app.config';
import { AppService } from './app.service';

import { User } from '../model/user'

import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class UserService {

  private capital = 10000;

  private user:User;

  constructor(private AppService:AppService) { }

  //SET AND GET USER
  setUser(res){

      this.user = new User(res);
      this.AppService.getCurrentCapital(res).subscribe(res => this.setCapital(res));
      this.AppService.getActiveStrategy(res).subscribe(res => this.setStrategy(res));

    return res = {response: 1, data: res};
  }

  getUser(){
    return this.user;
  }

  //AUTH
  authenticate() {
    return this.AppService.authenticateUser().map(res => this.printAuth(res));
  }

  //LOGIN
  loginUser(user) {
   return this.AppService.loginUser(user).map(res => { if(res.response == 1){return this.setUser(res.data)}else{return res}});
  }

  //REGISTER
  registerUser(user){
    return this.AppService.registerUser(user).map(res => { if(res.response == 1){return this.setUser(res.data)}else{return res}});
  }

  //INITIAL REGISTER CAPITAL
  addCapital(){
    return this.AppService.addCapital(this.user.id, this.capital);
  }

  //SET THE CURRENT CAPITAL FOR THIS USER
  setCapital(res){
    this.user.capital = res.data.amount;
    console.dir(this.user);
  }
  printAuth(res){
    console.log("Sessione corretta --> response:",res.response);
    return res;
  }

  //SET THE ACTIVE STRATEGY FOR THIS USER
  setStrategy(res){
    if(res.response == 1){
      this.user.strategy = res.data.list;
    }
  }
}
