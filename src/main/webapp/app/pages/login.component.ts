import {Component, ViewChild} from '@angular/core';
import { Cookie } from 'ng2-cookies';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';

@Component({
  templateUrl: 'login.component.html',
  providers:[UserService]
})
export class LoginComponent{
  constructor(private service:UserService, private router:Router) {
      if(Cookie.check('email')){
          console.log("già loggato");
          console.log(Cookie.get('email'));
          this.router.navigate(['dashboard']);
      }
  };
  user:any = {};
  public onSubmit(){
    this.service.login(this.user).subscribe(
        (res)=>{
            if(res.response==1){
                console.log(JSON.stringify(res));
                Cookie.set('email',res.data.email);
                Cookie.set('password',res.data.password);
                Cookie.set('id',res.data.id);
                console.log(Cookie.getAll());
                this.router.navigate(['dashboard']);
            }
            else{
                alert("Incorrect credentials: "+res.data);
                console.log("Incorrect credentials: ",res);
            }
        },
    (error) => {
      //console.error("ERROR"+error);
      console.log("LOG "+error);  //Prints 'error!' so throw works.
      alert("Server error, try later");
    });
    //this.form.reset();
  }


}
