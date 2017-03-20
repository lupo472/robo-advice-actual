import {IAssetClassStrategy} from "./iasset-class-strategy";
export interface IStrategy {
    list:IAssetClassStrategy[],
    name:string,
    risk:number
}
