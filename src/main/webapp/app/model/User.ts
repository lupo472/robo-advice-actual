import { Strategy } from './strategy';

export class User {
  email: string;
  password:string;
  // capital: number;
  // strategy: Strategy;

  constructor(user:any){
    this.email = user.email;
    //this.token = user.token;
    this.password = user.password;
  }

}
