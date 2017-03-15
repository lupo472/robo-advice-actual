import { Component } from '@angular/core';
import { UserService } from '../services/user.service';
import {Router} from "@angular/router";

@Component({
  templateUrl: 'register.component.html',
  providers:[UserService]
})
export class RegisterComponent {

  constructor(private UserService:UserService,private router:Router) { }

  user:any = {};

  public onSubmit(){
    if(this.user.password == this.user.password2){
    this.UserService.registerUser(this.user).subscribe(res =>
      {if(res.response == 1){
        //this.UserService.addCapital().subscribe(res => console.log("CAPITAL ADDED"));
        this.router.navigate(['pages/login']);
        }else{
        alert("Username already existing");}
      });
    }else{
      alert("Password Mismatch");
      this.user.password = "";
      this.user.password2 = "";
    }
  }
}
