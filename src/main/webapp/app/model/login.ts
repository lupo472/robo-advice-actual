import { CapitalData } from './capital-data';

export class Login {
  private email:string;
  private token:string;
  private capital:number;
  private capitalDatas:CapitalData[];
  private capitalLabels:string[];
  // private capitalData

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

  /*assignCapitalData(res) {
    if (res.response == 1) {

        let data:Array<number> = [];
        let date:string[] = [];

        res.data.forEach(item => {
            data.push(item.amount);
            date.push(item.date);
        });
        this.capitalDatas = [{data: data, label: this.getEmail()}];
        console.log("CAPITAL DATA: ", this.capitalData);
        this.capitalLabels = date;
        this.capital = data[data.length-1];
      } else {
        this.response = 'Come back tomorrow'
      }
}*/

}
