import { AssetClassStrategy } from './asset-class-strategy';
import {AssetClass} from "./asset-class";

export class Strategy {
    protected list: AssetClassStrategy[];
    private date:string;
    private active : boolean;
    public arrayPercentages: number[] = [];
    public arrayColor: string[] = [];
    public arrayColors: any[] = [];
    public arrayLabels: string[] = [];


    constructor(data?:any) {
      this.list = [];

      if(data){
          this.setActiveStrategy(data);
      }
    }

    setStrategyArray(strategyArray: any): void {
        /*let array = [];
        strategyArray.forEach((item)=>{
            array.push(new AssetClassStrategy(item.percentage,item.id,item.name));
        });*/
        this.list = strategyArray;
    }
    //Add an asset class to the list
    addAssetClassStrategy(assetClassStrategy: AssetClassStrategy): void {
        this.list.push(assetClassStrategy);
    }
    //Get the list of assetClassStrategies
    getStrategyArray(): AssetClassStrategy[] {
        return this.list;
    }
    //Send Strategy to backend once you choose a new one and click on button create
    sendStrategy() {
      return {"list":this.list}
    }
    //Create the chart of the strategy
    createChart(){
      for (let assetClassStrategy of this.list) {
          this.arrayPercentages.push(assetClassStrategy.getPercentage());
          this.arrayLabels.push(assetClassStrategy.getName());
          this.arrayColor.push(assetClassStrategy.assignColour());
          this.arrayColors = [{ backgroundColor: this.arrayColor, borderWidth: 3 }];
      }
    }

    getChartData(){
        this.createChart();
        let dataToReturn = {labels: this.arrayLabels,
                            datasets: [
                                {data: this.arrayPercentages,
                                    backgroundColor: this.arrayColors[0].backgroundColor,
                                borderWidth: this.arrayColors[0].borderWidth}],
                            colors: this.arrayColors};

        return dataToReturn;
    }

    setActiveStrategy(data){
        this.date = data.date;
        this.active = data.active;

        let list:AssetClassStrategy[] = [];

        for (let item of data.list){
            let itemToPush = new AssetClassStrategy(item.percentage, item.id, item.name);
            list.push(itemToPush);
        }

        this.setStrategyArray(list);

    }
}
