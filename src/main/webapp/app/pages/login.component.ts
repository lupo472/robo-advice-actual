import { Component, ViewChild } from '@angular/core';
import { Cookie } from 'ng2-cookies';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';
import {isNumber} from "util";

@Component({
  templateUrl: 'login.component.html'
})
export class LoginComponent {
  constructor(private UserService: UserService, private router: Router) {
    if (Cookie.check('id')) {
      this.router.navigate(['dashboard']);
        this.UserService.setUser({user:{
            id: Cookie.get('id'), email: Cookie.get('email'), password: "aaaaa"
        }});
    }

  }
  user: any = {};

  public onSubmit() {
    this.UserService.loginUser(this.user).subscribe(res => {
      if (res.response == 1) {
        this.setCookie(res.data)
      } else {
        alert(res.data);
      }
    });
  }

  public setCookie(data) {
    Cookie.set('token', data.token);
    Cookie.set('email', data.user.email);

    this.router.navigate(['dashboard']);
  }

}
