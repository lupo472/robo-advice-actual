import { Injectable, Inject } from '@angular/core';
import {AssetClassStrategies} from "../model/asset-class-strategies";
import {AssetService} from "../services/asset.service";
import {AssetClassStrategy} from "../model/asset-class-strategy";
import {AssetClass} from "../model/asset-class";

@Injectable()
export class MockAssetService{
    assetclass1=new AssetClass(1,"bonds");
    assetclass2=new AssetClass(2,"commodities");

    assetclassstrategy1=new AssetClassStrategy(95,this.assetclass1.getId(),this.assetclass1.getName());
    assetclassstrategy2=new AssetClassStrategy(5,this.assetclass2.getId(),this.assetclass2.getName());

    assetClassStrategies:AssetClassStrategies;
    assetClassStrategies = new AssetClassStrategies().setAssetClassStrategies([this.assetclassstrategy1,this.assetclassstrategy2]);

    getAssetClassStrategies(): AssetClassStrategy[] {
        console.log("############# getAssetClassStrategies IN TEST");
        return this.assetClassStrategies;
    }
}
