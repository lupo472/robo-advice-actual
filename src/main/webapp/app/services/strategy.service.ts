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
  historyStrategies:Strategies;
  currentStrategy:DefaultStrategy;
  dataHistory:any=[];
  period:number = 30;
  activeStrategy:Strategy;

  constructor(private AppService:AppService, private AssetService:AssetService) {
  }
  // CREATE A NEW STRATEGY
  createStrategy(currentStrategy){
    return this.AppService.setCustomStrategy(currentStrategy.sendStrategy()).map(res => console.log(res));
  }
  // STRATEGY JSON REMAPPING
  getDefaultStrategySet() {
    return this.AppService.getDefaultStrategySet().map(res => this.assignStrategy(res));
        //console.log(res));
        //this.assignStrategy(res));
  }
  assignStrategy(res) {
    this.strategies = new Strategies();
    this.customStrategy = new CustomStrategy();
    this.customStrategy.populateMap(this.AssetService.assetClassStrategies.getAssetClassStrategies());
    this.strategies.createStrategies(res);
    this.strategies.addStrategy(this.customStrategy);
    return this.strategies;
  }
  getHistoryStrategies() {
    return this.AppService.getHistoryStrategies().map(res => this.mapHistory(res));
  }
  mapHistory(res){
    if(res.response == 1 ) {
      this.historyStrategies = new Strategies();
      this.dataHistory=res.data;
      let startdate=new Date();
      startdate.setDate(startdate.getDate() - this.period);
      return this.historyStrategies.createChartDataHistory(res.data, startdate);
    }
  }
  refreshHistory(startdate){
    return this.historyStrategies.createChartDataHistory(this.dataHistory,startdate);
  }

  getActiveStrategy(){
    return this.AppService.getActiveStrategy().map(res => this.setActiveStrategy(res));
  }

  setActiveStrategy(res){
    if(res.response == 1) {
      this.activeStrategy = new Strategy(res.data);

      return this.activeStrategy.getChartData();
    }
  }
}
