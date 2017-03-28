import { Strategy } from './strategy';
import {IDefaultStrategy} from "./interfaces/idefault-strategy";
import {AssetClassStrategy} from "./asset-class-strategy";

export class DefaultStrategy extends Strategy implements IDefaultStrategy {
    name: string;
    risk: number;

    constructor() {
        super();
        this.list = [];
    }
    setName(name: string): void {
        this.name = name;
    }
    getName(): string {
        return this.name;
    }

    setDefaultStrategy(data){
        this.setName(data.name);
        data.list.forEach((item,index)=>{
            this.addAssetClassStrategy(new AssetClassStrategy(item.percentage,item.id,item.name));
        });
    }
    getDefaultStartegy(){
        return this.list;
    }
}
