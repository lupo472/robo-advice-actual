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
    //this.createStrategyList();
    this.name = "Customize";

    this.list = [];
    this.sumPercentage = 0;
    this.maxPercentage = 100;
  }
  //Get name of the custom strategy
  public getName(): string {
    return this.name;
  }
  public setName(name:string){
    this.name = name;
  }
  //Send to the component the array with the custom assetClassStrategies
  public getStrategyArray(): AssetClassStrategy[] {
    return this.list;
  }
  public setStrategyArray(strategyArray: any) : void {
    super.setStrategyArray(strategyArray);
    this.updateStrategyList();
  }
  createStrategyList() : void {
    let array = this.customList;
    console.log("array",array);
    this.list.forEach((item)=>{
      array[item.getId()-1] = item;
    });
    this.list = array;
    console.log("this.list",this.list);
    console.log("this.customlist",this.customList);
    this.populateMap();
  }
  /*
   *   Check if the active strategy is missing an asset class
   *   and assign to that the one with value 0 from the custom list
   *   which contains all the assets class with value 0
   */
  private updateStrategyList() : void {
    let array = this.customList;
    this.list.forEach((item)=>{
      array[item.getId()-1] = item;
    });
    this.list = array;
    //this.createStrategyList();
    this.updateMap();
  }
  //Update list with only the values in the map
  private updateList() : void {
    this.assetClassStrategiesMap.forEach((item)=>{
      this.list[item.getId()-1].setPercentage(item.getPercentage());
    });
  }
  //Get the Map with the custom assetClassStrategies
  public getAssetClassStrategyMap() : Map<number, AssetClassStrategy> {
    return this.assetClassStrategiesMap;
  }
  //Populate Map for the first time
  private populateMap() : void {
    this.list.forEach((item)=> {
      this.assetClassStrategiesMap.set(item.getId(),
          new AssetClassStrategy(item.getPercentage(),item.getId(),item.getName()));
    });
    this.updatePercentages();
  }
  //Update Map every time we change strategy
  private updateMap() : void {
    this.list.forEach((item)=>{
      let current = this.assetClassStrategiesMap.get(item.getId());
      current.setPercentage(item.getPercentage());
    });
    this.updatePercentages();
  }
  //Repaint the custom chart with new percentages
  public rePaint() :void {
    this.resetArrays();
    let array = [];
    this.assetClassStrategiesMap.forEach((item,index)=>{
      array.push(item);
    });
    this.setStrategyArray(array);
    this.createChart();
  }
  //Override method to send to backend just the assetClassStrategies > 0
  public sendStrategy() : any {
    let array = [];
    this.assetClassStrategiesMap.forEach((item,index)=>{
      if (item.getPercentage() > 0) {
        array.push(item);
      }
    });
    return {"list":array};
  }
  //Set percentage with sliders and calculate the max percentage we can set
  public setPercentageWithSlider(id,oldValue) : number {
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
  public resetSlider(strategy : Strategy) : void {
    this.getAssetClassStrategyMap().forEach((item, index) => {
      item.setPercentage(0);
    });
    this.updateList();
    this.rePaint();
  }
  //update Percentage
  private updatePercentages() : void {
    var sum = 0;
    this.assetClassStrategiesMap.forEach( (item,index) => [
      sum += item.getPercentage()
    ]);
    this.sumPercentage = sum;
    this.maxPercentage = 100 - this.sumPercentage;
  }
  private resetArrays() : void {
    this.arrayPercentages = [];
    this.arrayLabels = [];
    this.arrayColor = [];
    this.arrayColors = [];
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
}
