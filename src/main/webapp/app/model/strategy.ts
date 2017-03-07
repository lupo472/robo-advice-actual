import { AssetClassStrategy } from './asset-class-strategy';

export class Strategy {
  idUser:number;
  list:AssetClassStrategy[];

  constructor() {
    // this.idUser = 1;
  }

  setStrategyArray(strategyArray){
    this.list = strategyArray;
  }

  getStrategyArray(){
    return this.list;
  }

  setUserId(id) {
    this.idUser = id;
  }

  getUserId() {
    return this.idUser;
  }
}
