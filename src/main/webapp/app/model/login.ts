export class Login {
  private email:string;
  private token:string;

  constructor(login:any) {
    this.email = login.email;
    this.token = login.token;
  }

  getEmail() : string {
    return this.email;
  }
  getToken() : string {
    return this.token;
  }
}
