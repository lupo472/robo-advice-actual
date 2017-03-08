import { AssetClass } from './asset-class';
import { AssetClassStrategy } from './asset-class-strategy';

export class Asset {
  private _assetClass:AssetClassStrategy;
  private _amount:number;
  private _color:string;

  constructor(asset:any) {
    this._assetClass=asset.assetClassStrategy;
    this._amount=asset.amount;
    this._color=asset.color;
  }

  get assetClass(): AssetClassStrategy {
    return this._assetClass;
  }

  set assetClass(value: AssetClassStrategy) {
    this._assetClass = value;
  }

  get amount(): number {
    return this._amount;
  }

  set amount(value: number) {
    this._amount = value;
  }

  get color(): string {
    return this._color;
  }

  set color(value: string) {
    this._color = value;
  }

}
