import { Component } from '@angular/core';
import { UserService } from '../services/user.service';

@Component({
  templateUrl: 'register.component.html',
  providers:[UserService]
})
export class RegisterComponent {

  constructor(private service:UserService) { }
  
  user:any = {};
  
  public onSubmit(){
    
    this.service.register(this.user).subscribe(
    (res)=>{
    console.log(JSON.stringify(res));
       });
  }
  
}
