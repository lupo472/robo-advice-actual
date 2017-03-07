export class User {
  
  id: number;
  email: string;
  password: string;
  capital: number;
  
  constructor(user:any){
    this.id = user.id;
    this.email = user.email;
    this.password = user.password;
    this.capital = user.capital;
  }
  
}
