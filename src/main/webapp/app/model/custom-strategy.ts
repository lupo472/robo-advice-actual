import { AssetClassStrategy } from './asset-class-strategy';
import { Strategy } from './strategy';

export class CustomStrategy extends Strategy {
  private assetClassStrategiesMap:Map<number, AssetClassStrategy> = new Map<number, AssetClassStrategy>();
  private date:string;
  private sumPercentage:number;
  private maxPercentage:number;
  private oldValue:number;
  private name:string;

  constructor() {
    super();
    this.name = "custom";
    this.list = [];
    this.sumPercentage = 0;
    this.maxPercentage = 100;
    this.populateMap();
    this.createAssetClassStrategies();
  }
  createAssetClassStrategies(){
    this.assetClassStrategiesMap.forEach((item,index)=>{
      this.list.push(item);
      console.log("item",item);
    });
    console.log("ASSETCLASSSTRATEGIESCUSTOM",this.list);
  }
  sendStrategy(){
    let array = [];
    this.assetClassStrategiesMap.forEach((item,index)=>{
      if (item.getPercentage() > 0) {
        array.push(item);
      }
    });
    return {"list":array};
  }
  getStrategyArray(): AssetClassStrategy[] {
    return this.list;
  }
  populateMap(){
    // assetClassStrategies.forEach(()=>{
    //   console.log(item);
    // });
    this.assetClassStrategiesMap.set(1,new AssetClassStrategy(0,1,""));
    this.assetClassStrategiesMap.set(2,new AssetClassStrategy(0,2,""));
    this.assetClassStrategiesMap.set(3,new AssetClassStrategy(0,3,""));
    this.assetClassStrategiesMap.set(4,new AssetClassStrategy(0,4,""));
  }
  getAssetClassStrategyMap(){
    return this.assetClassStrategiesMap;
  }
  setPercentageWithSlider(id,oldValue) {
    let currentSlider = this.assetClassStrategiesMap.get(id);
    if (currentSlider.getPercentage() - oldValue + this.sumPercentage > 100){
      if (this.maxPercentage !=0) {
        if (currentSlider.getPercentage() > oldValue) {
          currentSlider.setPercentage(this.maxPercentage + oldValue);
        } else {
          currentSlider.setPercentage(this.maxPercentage);
        }
      } else {
        if (currentSlider.getPercentage() > oldValue) {
            currentSlider.setPercentage(oldValue);
          }
      }
    }
    this.assetClassStrategiesMap.set(currentSlider.getId(),currentSlider);
    var sum = 0;
    this.assetClassStrategiesMap.forEach( (item,index) => [
      sum += item.getPercentage()
    ]);
    this.sumPercentage = sum;
    this.maxPercentage = 100 - this.sumPercentage;
    console.log("MAP",this.assetClassStrategiesMap);
    return currentSlider.getPercentage();
  }
  getName(): string {
    return this.name;
  }
}
