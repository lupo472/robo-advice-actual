import { Strategy } from './strategy';

export class User {
  
  id: number;
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
