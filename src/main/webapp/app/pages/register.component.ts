import { Component } from '@angular/core';
import { UserService } from '../services/user.service';
import {Router} from "@angular/router";

@Component({
  templateUrl: 'register.component.html',
  providers:[UserService]
})
export class RegisterComponent {

  constructor(private service:UserService,private router:Router) { }
  
  user:any = {};
  
  public onSubmit(){
    console.log(this.user.password2);
    if(this.user.password==this.user.password2){
      this.service.register(this.user).subscribe(
          (res)=>{
            console.log(JSON.stringify(res));
            this.router.navigate(['pages/login']);
            });
    }else{
      alert("Password mismatch");
      this.user.password="";
      this.user.password2="";
    }

  }
  
}
