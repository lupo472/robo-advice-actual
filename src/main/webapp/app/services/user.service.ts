
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
    
    return res = {response: 1, data: res};
  }
  
  getUser(){
    return this.user;
  }
  
  //LOGIN 
  loginUser(user) {
   return this.AppService.loginUser(user).map(res => { if(res.response == 1){return this.setUser(res.data)}else{return res}});
  }
  
  //REGISTER
  registerUser(user){
    return this.AppService.registerUser(user).map(res => { if(res.response == 1){return this.setUser(res.data)}else{return res}});
  }

  addCapital(){
    return this.AppService.addCapital(this.user.id, this.capital); 
  }
  
  setCapital(res){
    this.user.capital = res.data.amount;
    console.log("UTENTE: " + JSON.stringify(this.user));
  }
    
}
