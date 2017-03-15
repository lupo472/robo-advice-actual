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
  constructor(private UserService: UserService, private router: Router) {
    super()
  }

  ngOnInit(): void {


    if (Cookie.check('token')) {
      this.UserService.setLogin({user:{
        email: Cookie.get('email'), token: Cookie.get('token')
      }});
    } else {
      Cookie.delete("token");
      this.router.navigate(['pages/login']);
    }


  }

  /* if(Cookie.check('id')){
   this.UserService.setUser({user:{
   id: Cookie.get('id'), email: "a@a", password: "aaaaaa"}});
   }else{
   console.log("Not Logged");

   }*/

  //this.user = this.UserService.getUser();


}
