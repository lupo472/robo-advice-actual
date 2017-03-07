
import { Injectable, Inject } from '@angular/core';
import { AppConfig } from './app.config';
import { AppService } from './app.service';

import { User } from '../model/user'

import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class UserService {
  
  private capital = 10000;
  
  public user:User;

  constructor(private AppService:AppService) { }
  
  //SET AND GET USER
  setUser(res){
    if(res.response == 1){
      this.user = new User(res.data);
    }
      return res;
  }
  
  getUser(){
    return this.user;
  }
  
  //LOGIN 
  loginUser(user) {
   return this.AppService.loginUser(user).map(res => this.setUser(res));
  }
  
  //REGISTER
  registerUser(user){
    return this.AppService.registerUser(user).map(res => this.setUser(res));
  }

  addCapital(){
    return this.AppService.addCapital(this.user.id, this.capital); 
  }
  
  getCurrentCapital(user){
    return this.AppService.getCurrentCapital(user).map(res => this.setCapital(res));
  }
  
  setCapital(res){
    this.user.capital = res.data.amount;
  }
    
}
