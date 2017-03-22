import { AssetClassStrategy } from './asset-class-strategy';
import { Strategy } from './strategy';

export class CustomStrategy extends Strategy {
  private assetClassStrategiesMap:Map<number, AssetClassStrategy> = new Map<number, AssetClassStrategy>();
  private sumPercentage:number;
  private maxPercentage:number;
  private name:string;
  private customList:AssetClassStrategy[];

  constructor(customList) {
    super();
    //Initialize custom list of assetClassStrategies with the ones got from backend
    this.customList = customList;
    this.name = "active";
    this.list = [];
    this.sumPercentage = 0;
    this.maxPercentage = 100;
  }
  createAssetClassStrategies(){
    this.assetClassStrategiesMap.forEach(item =>{
      console.log("THIS.LIST",item);
      this.list.push(item);
    });
  }
  resetArrays() : void {
    this.arrayPercentages = [];
    this.arrayLabels = [];
    this.arrayColor = [];
    this.arrayColors = [];
  }
  //Repaint the custom chart with new percentages
  rePaint() :void {
    this.resetArrays();
    let array = [];
    this.assetClassStrategiesMap.forEach((item,index)=>{
      array.push(item);
    });
    this.setStrategyArray(array);
    console.log("array",array);
    this.createChart();
  }
  //Override method to send to backend just the assetClassStrategies > 0
  sendStrategy(){
    let array = [];
    this.assetClassStrategiesMap.forEach((item,index)=>{
      if (item.getPercentage() > 0) {
        array.push(item);
      }
    });
    return {"list":array};
  }
  /*
  *   Check if the active strategy is missing an asset class
  *   and assign to that the one with value 0 from the custom list
  *   which contains all the assets class with value 0
  */
  updateStrategyList() : void {
    let array = this.customList;
    this.list.forEach((item)=>{
      array[item.getId()-1] = item;
    });
    this.list = array;
    this.updateMap();
    this.updatePercentages();
  }
  //Send to the component the array with the custom assetClassStrategies
  getStrategyArray(): AssetClassStrategy[] {
    console.log("ASSETCLASSSTRATEGYMAP",this.assetClassStrategiesMap);
    return this.list;
  }
  //Populate Map for the first time
  populateMap() : void {
    this.customList.forEach((item)=> {
      this.assetClassStrategiesMap.set(item.getId(),
      new AssetClassStrategy(item.getPercentage(),item.getId(),item.getName()));
    });
    this.updatePercentages();
  }
  //Update Map every time we change strategy
  updateMap() : void {
    this.list.forEach((item)=>{
      let current = this.assetClassStrategiesMap.get(item.getId());
      current.setPercentage(item.getPercentage());
    });
  }
  //Update list with the values in the map
  updateList() : void {
    this.assetClassStrategiesMap.forEach((item)=>{
      this.list[item.getId()-1].setPercentage(item.getPercentage());
    });
  }
  //Get the Map with the custom assetClassStrategies
  getAssetClassStrategyMap() : Map<number, AssetClassStrategy> {
    return this.assetClassStrategiesMap;
  }
  //Set percentage with sliders and calculate the max percentage we can set
  setPercentageWithSlider(id,oldValue) : number {
    console.log("list",this.list);
    console.log("map",this.assetClassStrategiesMap);
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
    this.updatePercentages();
    return currentSlider.getPercentage();
  }
  //Resets all sliders and chart after clicking cancel button
  resetSlider(strategy : Strategy) : void {
      this.getAssetClassStrategyMap().forEach((item,index)=>{
        item.setPercentage(0);
      });
      this.updateList();
    this.rePaint();
  }
  //Preset slider,map and list to the active startegy of the user
  /*resetSlider(strategy : Strategy) : void {
    this.getAssetClassStrategyMap().forEach((item,index)=>{
      item.setPercentage(0);
      strategy.getStrategyArray().forEach((element)=>{
        if (item.getId() == element.getId()){

          item.setPercentage(element.getPercentage());
        }
      });
    });
    this.updateList();
    this.rePaint();
  }*/
  //Update maxPercentage and sumPercentage
  updatePercentages() : void {
    var sum = 0;
    this.assetClassStrategiesMap.forEach( (item,index) => [
      sum += item.getPercentage()
    ]);
    this.sumPercentage = sum;
    this.maxPercentage = 100 - this.sumPercentage;
  }
  //Get name of the custom strategy
  getName(): string {
    return this.name;
  }
}
