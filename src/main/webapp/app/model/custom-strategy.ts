import { AssetClassStrategy } from './asset-class-strategy';
import { Strategy } from './strategy';

export class CustomStrategy extends Strategy {
  private assetClassStrategiesMap:Map<number, AssetClassStrategy> = new Map<number, AssetClassStrategy>();
  private sumPercentage:number;
  private maxPercentage:number;
  private oldValue:number;
  private name:string;

  constructor() {
    super();
    this.name = "active";
    this.list = [];
    this.sumPercentage = 0;
    this.maxPercentage = 100;
  }
  createAssetClassStrategies(){
    this.assetClassStrategiesMap.forEach(item =>{
      this.list.push(item);
    });
  }
  rePaint(){
    this.arrayPercentages = [];
    this.arrayLabels = [];
    this.arrayColor = [];
    this.arrayColors = [];
    let array = [];
    this.assetClassStrategiesMap.forEach((item,index)=>{
      array.push(item);
    });
    this.setStrategyArray(array);
    this.createChart();
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
  populateMap(assetClassStrategies:AssetClassStrategy[]){
    assetClassStrategies.forEach(item => {
      this.assetClassStrategiesMap.set(item.getId(),
      new AssetClassStrategy(item.getPercentage(),item.getId(),item.getName()));
    });
    this.createAssetClassStrategies();
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
  resetSlider(){
      this.getAssetClassStrategyMap().forEach((item,index)=>{
        item.setPercentage(0);
      });
    this.rePaint();
  }
  getName(): string {
    return this.name;
  }
}
