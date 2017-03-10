import { Strategy } from './strategy';

export class User {
  
  id: any;
  email: string;
  password: string;
  capital: number;
  strategy: Strategy;
  
  constructor(user:any){
    this.id = user.id;
    this.email = user.email;
    this.password = user.password;
  }
  
}
