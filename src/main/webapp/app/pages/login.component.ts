import { Component, ViewChild } from '@angular/core';
import { Cookie } from 'ng2-cookies';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';

@Component({
  templateUrl: 'login.component.html'
})
export class LoginComponent {
  constructor(private UserService: UserService, private router: Router) {
    if (Cookie.check('id')) {
      this.UserService.setUser({
        id: Cookie.get('id')
      });

      this.router.navigate(['dashboard']);
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
    Cookie.set('id', data.id);

    this.router.navigate(['dashboard']);
  }

}
