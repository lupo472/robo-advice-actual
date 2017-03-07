import { AssetClass } from './asset-class';

export class Asset {
  private _id:number;
  private _name:string;
  private _dataSource: string;
  private _assetClass:AssetClass;
  private _percentage:number;

  constructor(asset:any) {
    this._id = asset.id;
    this._name = asset.name;
    this._dataSource=asset.dataSource;
    this._assetClass=asset.assetClass;
    this._percentage=asset.percentage
  }


  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }

  get name(): string {
    return this._name;
  }

  set name(value: string) {
    this._name = value;
  }

  get dataSource(): string {
    return this._dataSource;
  }

  set dataSource(value: string) {
    this._dataSource = value;
  }

  get assetClass(): AssetClass {
    return this._assetClass;
  }

  set assetClass(value: AssetClass) {
    this._assetClass = value;
  }

  get percentage(): number {
    return this._percentage;
  }

  set percentage(value: number) {
    this._percentage = value;
  }




}
