import { AssetClassStrategy } from './asset-class-strategy';

export class Strategy {
    protected list: AssetClassStrategy[];
    private date:string;
    private active : boolean;
    public arrayPercentages: number[] = [];
    public arrayColor: string[] = [];
    public arrayColors: any[] = [];
    public arrayLabels: string[] = [];

    constructor() {
      this.list = [];
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

}
