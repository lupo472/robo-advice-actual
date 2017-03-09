import { AssetClassStrategy } from './asset-class-strategy';

export class DefaultStrategy {
  name:string;
  list:AssetClassStrategy[];

  constructor(name) {
    this.name = name;
    this.list = [];
  }

  setName(name){
    this.name = name;
  }
  getName(){
    return name;
  }
  setList(list){
    this.list.push(list);
  }
  getList(){
    return this.list;
  }

}
