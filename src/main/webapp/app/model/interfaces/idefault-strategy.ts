import {IAssetClassStrategy} from "./iasset-class-strategy";
export interface IDefaultStrategy {
    list:IAssetClassStrategy[];
    name:string;
    risk:number;
}
