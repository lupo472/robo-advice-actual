import { AssetClass } from './asset-class';
import { Asset } from './asset';

export class PortfolioElem {
  private _asset:Asset;
  private _units:number;
  private _value:number;

  constructor(portfolioelem:any) {
    this._asset = portfolioelem.id;
    this._units = portfolioelem._units;
    this._value=portfolioelem._value;
  }

  get asset(): Asset {
    return this._asset;
  }

  set asset(value: Asset) {
    this._asset = value;
  }

  get units(): number {
    return this._units;
  }

  set units(value: number) {
    this._units = value;
  }

  get value(): number {
    return this._value;
  }

  set value(value: number) {
    this._value = value;
  }


}
