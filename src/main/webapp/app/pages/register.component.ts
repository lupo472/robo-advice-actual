import { Component } from '@angular/core';
import { UserService } from '../services/user.service';

@Component({
  templateUrl: 'register.component.html',
  providers:[UserService]
})
export class RegisterComponent {

  constructor(private service:UserService) { }
  
  email:string;
  password:string;
  user:any;
  
  public register(){
    
    this.user={"email": this.email, "password": this.password};
    
    this.service.register(this.user).subscribe(
    (res)=>{
    console.log(JSON.stringify(res));
       });
  }
  
}
