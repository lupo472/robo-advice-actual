import { PortfolioElem } from './portfolioElem';

export class Portfolio {

  private _idUser:number;
  private _list:Array<PortfolioElem>;
  private _date:string;

  constructor(portfolio:any) {
    this._idUser = portfolio._idUser;
    this._list = portfolio._list;
    this._date = portfolio._date;
  }
  get idUser(): number {
    return this._idUser;
  }

  set idUser(value: number) {
    this._idUser = value;
  }

  get list(): Array<PortfolioElem> {
    return this._list;
  }

  set list(value: Array<PortfolioElem>) {
    this._list = value;
  }

  get date(): string {
    return this._date;
  }

  set date(value: string) {
    this._date = value;
  }


}
