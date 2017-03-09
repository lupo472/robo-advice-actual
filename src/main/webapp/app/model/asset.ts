import { AssetClass } from './asset-class';
import { AssetClassStrategy } from './asset-class-strategy';

export class Asset extends AssetClassStrategy {
  assetClass:AssetClass;
  amount:number;
  color:string;

  constructor(percentage: number,assetClass: AssetClass) {
    super(percentage,assetClass);
    // this._assetClass=asset.assetClassStrategy;
    //this._amount=asset.amount;
    //this._colour=colour;

  }

  getAssetClass(): AssetClass {
    return this.assetClass;
  }

  setAssetClass(value: AssetClass) {
    this.assetClass = value;
  }

  getAmount(): number {
    return this.amount;
  }

  setAmount(value: number) {
    this.amount = value;
  }

  getColor(): string {
    return this.color;
  }

  setColor(value: string) {
    this.color = value;
  }

}
