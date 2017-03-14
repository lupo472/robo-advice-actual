import {AssetClass} from './asset-class';

export class AssetClassStrategy extends AssetClass {
    percentage: number;

    constructor(percentage, id, name) {
        super(id, name);
        this.percentage = percentage;
    }

    setPercentage(percentage) {
        this.percentage = percentage;
    }

    getPercentage() {
        return this.percentage;
    }
    // setName(name){
    //   super.setName(name);
    // }
    // getName(){
    //   return super.getName();
    // }
    // getId(){
    //   return super.getId();
    // }
    // setId(id){
    //   super.setId(id);
    // }
}
