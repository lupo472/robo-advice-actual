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

    setStrategyArray(strategyArray: AssetClassStrategy[]): void {
        this.list = strategyArray;
    }

    addAssetClassStrategy(assetClassStrategy: AssetClassStrategy): void {
        this.list.push(assetClassStrategy);
    }

    getStrategyArray(): AssetClassStrategy[] {
        return this.list;
    }
    sendStrategy() {
      return {"list":this.list}
    }
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
        console.log("DATAELABORATED: ", dataToReturn)
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

        console.log("LIST: ",this.getStrategyArray())
    }
}
