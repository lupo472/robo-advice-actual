import {Component, OnInit} from '@angular/core';
import {Cookie} from "ng2-cookies";
import { UserService } from './services/user.service';
import {Router} from "@angular/router";
import {isNumber} from "util";


@Component({
  selector: 'body',
  template: '<router-outlet></router-outlet>'
})
export class AppComponent extends OnInit {

  constructor(public UserService:UserService, public router:Router) {
      super();
  }

  ngOnInit(): void {
      if(Cookie.check("token") && Cookie.check("email")){
          this.UserService.setLogin({email: Cookie.get("email"), token: Cookie.get("token")});
      }else{
          this.router.navigate(['pages/login']);
      }
  }

}
