import { Injectable, Inject } from '@angular/core';
import { AppConfig } from './app.config';
import { AppService } from './app.service';
import { AssetService } from './asset.service';
import { Strategy } from '../model/strategy';
import { DefaultStrategy } from '../model/default-strategy';
import { CustomStrategy } from '../model/custom-strategy';
import { Strategies } from '../model/strategies';
import { Cookie } from 'ng2-cookies';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class StrategyService {
  strategies:Strategies;
  customStrategy:CustomStrategy;
  array:Strategy[];
  currentStrategy:DefaultStrategy;

  constructor(private AppService:AppService, private AssetService:AssetService) {
    this.strategies = new Strategies();
  }
  // CREATE A NEW STRATEGY
  createStrategy(currentStrategy){
    return this.AppService.setCustomStrategy(currentStrategy.sendStrategy()).map(res => console.log(res));
  }
  // STRATEGY JSON REMAPPING
  getDefaultStrategySet() {
    return this.AppService.getDefaultStrategySet().map(res => console.log(res));
        //console.log(res));
        //this.assignStrategy(res));
  }
  assignStrategy(res) {
    //this.strategies = new Strategies();
    this.customStrategy = new CustomStrategy();
    this.customStrategy.populateMap(this.AssetService.assetClassStrategies.getAssetClassStrategies());
    this.strategies.createStrategies(res);
    this.strategies.addStrategy(this.customStrategy);
    return this.strategies;
  }
  getHistoryStrategies() {
    return this.AppService.getHistoryStrategies().map(res => {return res});
  }

}
