import {IStrategy} from "./istrategy";

export interface IDefaultStrategy extends IStrategy {
    name:string;
    risk:number;
}
