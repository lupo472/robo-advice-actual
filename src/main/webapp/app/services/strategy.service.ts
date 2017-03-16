
import { Injectable, Inject } from '@angular/core';
import { AppConfig } from './app.config';
import { AppService } from './app.service';
import { AssetService } from './asset.service';
import { Strategy } from '../model/strategy';
import { DefaultStrategy } from '../model/default-strategy';
import { CustomStrategy } from '../model/custom-strategy';
import { DefaultStrategies } from '../model/default-strategies';
import { Strategies } from '../model/strategies';
import { AssetClassStrategy } from '../model/asset-class-strategy';
import { AssetClass } from '../model/asset-class';
import { Cookie } from 'ng2-cookies';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class StrategyService {
  assetClassStrategy:AssetClassStrategy;
  defaultStrategy:DefaultStrategy;

  // assetClass:AssetClass;
  strategy:Strategy;

  // result:any;
  defaultStrategies:DefaultStrategies;

  strats:Strategies;
  customStrategy:CustomStrategy;

  array:Strategy[];
  // public strategySet = [];
  public currentStrategy:DefaultStrategy;

  //isCustom:boolean;
  constructor(private AppService:AppService, private AssetService:AssetService) {
  }

  createDefaultStrategy(currentDefaultStrategy){
    return this.AppService.setCustomStrategy(currentDefaultStrategy.sendCurrentStrategy()).map(res => console.log(res));
  }

  // setCustomStrategy() {
  //   this.strategy = new Strategy();
  //   //this.strategy.setUserId(Cookie.get('id'));
  //   var array = [];
  //   this.strategies.forEach( (k,v) => [
  //     array.push(k)
  //   ]);
  //   this.strategy.setStrategyArray(array);
  //     return this.AppService.setCustomStrategy(this.strategy).map(res => console.log(res));
  // }

  // STRATEGY JSON REMAPPING
  getDefaultStrategySet() {
    return this.AppService.getDefaultStrategySet().map(res => this.assignStrategy(res));
  }

  assignStrategy(res) {
    this.strats = new Strategies();
    this.customStrategy = new CustomStrategy();
    this.strats.createStrategies(res.data);
    console.log("STRATEGIES",this.strats.getStrategies());
    this.array = this.strats.getStrategies();
    this.array.push(this.customStrategy);
    return this.array;
  }


}
