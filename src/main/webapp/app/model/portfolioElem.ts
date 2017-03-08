
import { AssetClassStrategy } from './asset-class-strategy';

export class PortfolioElem {
  private _assetClassStrategy:AssetClassStrategy;
  private _value:number;

  constructor(portfolioelem:any) {
    this._assetClassStrategy = portfolioelem._assetClassStrategy;
    this._value = portfolioelem._value;
  }

  get assetClassStrategy(): AssetClassStrategy {
    return this._assetClassStrategy;
  }

  set assetClassStrategy(value: AssetClassStrategy) {
    this._assetClassStrategy = value;
  }

  get value(): number {
    return this._value;
  }

  set value(value: number) {
    this._value = value;
  }
}
