import { AssetClass } from './asset-class';

export class Strategy {
  assetClass:AssetClass;
  percentage:number;

  constructor(percentage,assetClass) {
    this.percentage = percentage;
    this.assetClass = assetClass;
  }

  setAssetClass(assetClass) {
    this.assetClass = assetClass;
  }

  setPercentage(percentage){
    this.percentage = percentage;
  }

  getAssetClass() {
    return this.assetClass;
  }

  getPercentage() {
    return this.percentage;
  }
}
