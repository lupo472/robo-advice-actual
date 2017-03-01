import { Component } from '@angular/core';
import { UserService } from '../services/user.service';

@Component({
  templateUrl: 'login.component.html',
  providers:[UserService]
})
export class LoginComponent {

  constructor(private service:UserService) { }

  user:any = {};
  public onSubmit(){
    this.service.login(this.user).subscribe(
        (res)=>{
          console.log(JSON.stringify(res));
          alert(res.data);
        },
    (error) => {
      console.log
      //console.error("ERROR"+error);
      console.log("LOG "+error);  //Prints 'error!' so throw works.
      alert("Problemi con il server, riprova più tardi");
    });
    //this.form.reset();
  }


}
