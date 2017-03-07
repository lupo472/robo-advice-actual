import {Component, ViewChild} from '@angular/core';
import { Cookie } from 'ng2-cookies';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';

@Component({
  templateUrl: 'login.component.html',
  providers:[UserService]
})
export class LoginComponent{
  constructor(private UserService:UserService, private router:Router) {
      if(Cookie.check('email')){
          console.log("Already Logged");
          this.router.navigate(['edit']);
      }
  };
  user:any = {};

  public onSubmit(){
    this.UserService.loginUser(this.user).subscribe(res => 
      {if(res.response == 1){
        this.UserService.getCurrentCapital(res.data).subscribe();
        this.setCookie(res.data)
        }else{
        alert("Wrong Username or Password");}
      });
  }

  public setCookie(user){
      Cookie.set('email',user.email);
      Cookie.set('password',user.password);
      Cookie.set('id',user.id);
      this.router.navigate(['dashboard']);
  }

}
