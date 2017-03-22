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

  constructor(private AppService:AppService, private AssetService:AssetService) {
  }
  // CREATE A NEW STRATEGY
  createStrategy(currentStrategy){
    return this.AppService.setCustomStrategy(currentStrategy.sendStrategy()).map(res => console.log(res));
  }
  // STRATEGY JSON REMAPPING
  getDefaultStrategySet() {
    return this.AppService.getDefaultStrategySet().map(res => this.assignStrategy(res));
  }
  assignStrategy(res) {
    this.strategies = new Strategies();
    //Create a custom strategy with all the asset classes from backend
    this.customStrategy = new CustomStrategy(this.AssetService.assetClassStrategies.getAssetClassStrategies());
    this.customStrategy.populateMap();
    this.strategies.createStrategies(res);
    if (this.activeStrategy != undefined) {
      this.customStrategy.setStrategyArray(this.activeStrategy.getStrategyArray());
      this.customStrategy.updateStrategyList();
      console.log("custom",this.customStrategy);
    }
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
