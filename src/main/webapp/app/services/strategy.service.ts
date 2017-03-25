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
  list:AssetClassStrategy[];
  public myActiveStrategyChart:MyActiveStrategyAmChart;

  constructor(private AppService:AppService, private AssetService:AssetService) {
  }
  // CREATE A NEW STRATEGY
  createStrategy(){
    let currentStrategy = this.strategies.getCurrentStrategy();
    return this.AppService.setCustomStrategy(currentStrategy.sendStrategy()).map(res => {return res});
  }
  // STRATEGY JSON REMAPPING
  getDefaultStrategySet() {
    return this.AppService.getDefaultStrategySet().map(defaultStrategies => this.assignStrategy(defaultStrategies));
  }
  assignStrategy(defaultStrategies) {
    this.strategies = new Strategies();
    this.strategies.createStrategies(defaultStrategies);
    this.customStrategy = new CustomStrategy(this.AssetService.assetClassStrategies.getAssetClassStrategies());
    if (this.activeStrategy != undefined) {
      this.customStrategy.setStrategyArray(this.activeStrategy.getStrategyArray());
    }
    this.strategies.addStrategy(this.customStrategy);
    return this.strategies;
  }
  /***************************TESTING*****************************************/
  getHistoryChart() {
    return this.AppService.getHistoryStrategies().map(res => this.historyChart(res));
  }
  historyChart(res){
    if(res.response == 1 ) {
      this.historyStrategies = new Strategies();
      this.dataHistory=res.data;
      let startdate=new Date();
      startdate.setDate(startdate.getDate() - this.period);
      return this.historyStrategies.createHistoryChartOptions(res.data, startdate);
    }
  }
  /******************************#********************************************/
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
  //GET ACTIVE STARTEGY IF I HAVE ONE
  getActiveStrategy(){
    return this.AppService.getActiveStrategy()
        .map(activeStrategy => {
          if(activeStrategy.response == 1) {
            this.activeStrategy = new Strategy(activeStrategy.data);
            return this.activeStrategy;
          }
        });
  }

  createTrendLabelHistory(labels){
    //labels=['2017-03-20','2017-03-21'];

    let trendLabels:any=[];
    let portfolio=this.AssetService.getPortfolio().getData();
    console.log("PORTFOLIO",portfolio);
    console.log("labels",labels);


    labels.forEach((label,i)=>{
      portfolio.labels.forEach((labelPortfolio,j)=>{
        console.log("labelPortfolio",labelPortfolio);
        if(label===labelPortfolio){
          console.log("portfolio.datasets[0].data[j]",portfolio.datasets[0].data[j]);
          trendLabels[i]={};

          trendLabels[i].startvalue=(portfolio.datasets[0].data[j]);
          if(i!==0){
            trendLabels[i-1].endvalue=trendLabels[i].startvalue;
          }
        }
      });
      if(i+1===labels.length){ //case of the last strategy that is analyzed
        console.log("strategie terminate:",i+1);
        trendLabels[i].endvalue=(portfolio.datasets[0].data[portfolio.datasets[0].data.length-1]);
      }else{
        //trendLabels[i].endvalue=(portfolio.datasets[0].data[j+1]);
      }
    });
    console.log("trendLabels",trendLabels);
    return trendLabels;

  }
}
