import { Component } from '@angular/core';
import { UserService } from '../services/user.service';

@Component({
  templateUrl: 'register.component.html',
  providers:[UserService]
})
export class RegisterComponent {

  constructor(private service:UserService) {
    this.service.register().subscribe(
    (res)=>{
    console.log(JSON.stringify(res));
       });
   }
}
