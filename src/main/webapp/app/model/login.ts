export class Login {
  private email:string;
  private token:string;

  constructor(login:any) {}

  getEmail() : string {
    return this.email;
  }
  getToken() : string {
    return this.token;
  }
}
