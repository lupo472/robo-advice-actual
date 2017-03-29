import { Injectable, Inject } from '@angular/core';
import { AppConfig } from './app.config';
import { AppService } from './app.service';
import { AssetService } from './asset.service';
import { Strategy } from '../model/strategy';
import { AssetClassStrategy } from '../model/asset-class-strategy';
import { DefaultStrategy } from '../model/default-strategy';
import { CustomStrategy } from '../model/custom-strategy';
import { Strategies } from '../model/strategies';
import { Cookie } from 'ng2-cookies';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';
import {MyActiveStrategyAmChart} from "../model/my-active-strategy-am-chart";
import {HistoryAmChart} from "../model/history-am-chart";

@Injectable()
export class StrategyService {
  strategies:Strategies;
  customStrategy:CustomStrategy;
  array:Strategy[];
  historyStrategies:HistoryAmChart;
  currentStrategy:DefaultStrategy;
  dataHistory:any=[];
  period:number = 30;
  activeStrategy:Strategy;
  adviceStrategy:DefaultStrategy;
  list:AssetClassStrategy[];
  strategySended:DefaultStrategy;
  public myActiveStrategyChart:MyActiveStrategyAmChart;

  constructor(private AppService:AppService, private AssetService:AssetService) {
  }
  // CREATE A NEW STRATEGY
  createStrategy(){
    let currentStrategy = this.strategies.getCurrentStrategy();
    return this.AppService.setCustomStrategy(currentStrategy.sendStrategy()).map(res => {return res});
  }
  changeToAdviceStrategy(currentStrategy){
    return this.AppService.setCustomStrategy(currentStrategy.sendStrategy()).map(res => {return res});
  }
  // STRATEGY JSON REMAPPING
  getDefaultStrategySet() {
    return this.AppService.getDefaultStrategySet().map(defaultStrategies => this.assignStrategy(defaultStrategies));
  }
  assignStrategy(defaultStrategies) {
    this.strategies = new Strategies();
    this.strategies.createStrategies(defaultStrategies);
    let assetClasses = this.AssetService.assetClassStrategies.getAssetClassStrategies();
    this.customStrategy = new CustomStrategy(assetClasses);
    this.customStrategy.createStrategyList();
    if (this.activeStrategy != undefined) {
      this.customStrategy.setStrategyArray(this.activeStrategy.getStrategyArray());
      this.customStrategy.setName("Active");
    }
    this.strategies.addStrategy(this.customStrategy);
    return this.strategies;
  }
  getAdvice(period){
    return this.AppService.getAdvice(period).map(strategyAdvice => this.assignStrategyAdvice(strategyAdvice));
  }
  assignStrategyAdvice(strategyAdvice){
    if (strategyAdvice.response == 1) {
      this.adviceStrategy = new DefaultStrategy();
      this.adviceStrategy.setDefaultStrategy(strategyAdvice.data);
      return this.adviceStrategy;
    } else if (strategyAdvice.response == 0) {
    }
  }

  /***************************TESTING CHART HISTORY VERSION 2*****************************************/
  getHistoryChart() {
    return this.AppService.getHistoryStrategies().map(res => this.historyChart(res));
  }
  historyChart(res){
    if(res.response == 1 ) {
      this.historyStrategies = new HistoryAmChart();
      this.dataHistory=res.data;
      let startdate=new Date();
      startdate.setDate(startdate.getDate() - this.period);
      let portfolio:any;

      if(this.AssetService.getPortfolio()){
        portfolio=this.AssetService.getPortfolio().getData(); //get portfolio to create trend labels
      }
      else{
        portfolio=false;
      }
      return this.historyStrategies.createHistoryChartOptions(res.data, startdate,portfolio);
    }
  }
  refreshHistory(startdate){
    let portfolio=this.AssetService.getPortfolio().getData();
    return this.historyStrategies.createHistoryChartOptions(this.dataHistory,startdate,portfolio);
  }
  setDefaultStrategySended(strategy){
    this.strategySended = new DefaultStrategy;
    this.strategySended = strategy;
  }
  getDefaultStrategySended(){
    return this.strategySended;
  }
  resetStrategySended(){
    this.strategySended = undefined;
  }


  /*****************************CHART HISTORY VERSION 1**************************************/
  /*getHistoryStrategies() {
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

  }*/

  getActiveStrategy(){
    return this.AppService.getActiveStrategy()
        .map(activeStrategy => {
          if(activeStrategy.response == 1) {
            this.activeStrategy = new Strategy(activeStrategy.data);
            return this.activeStrategy;
          }
        });
  }


}
