import { AssetClassStrategy } from './asset-class-strategy';
import { Strategy } from './strategy';

export class CustomStrategy extends Strategy {
  private assetClassStrategies:Map<number, AssetClassStrategy> = new Map<number, AssetClassStrategy>();
  private date:string;
  private sumPercentage:number;
  private maxPercentage:number;
  private oldValue:number;

  constructor() {}

  createAssetClassStrategies(data) {
    data.forEach((item,index)=>{
      this.assetClassStrategies.set(item.id,
        this.addAssetClassStrategy(0,item.id,""));
    });
  }
  addAssetClassStrategy(percentage,id,name){
    return new AssetClassStrategy(percentage,id,name);
  }
  getAssetClassStrategies(){

  }
}
