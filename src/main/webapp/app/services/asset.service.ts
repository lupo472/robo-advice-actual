import { Injectable, Inject } from '@angular/core';
import { AppConfig } from './app.config';
import { AppService } from './app.service';
import { AssetClassStrategy } from '../model/asset-class-strategy';
import { AssetClassStrategies } from '../model/asset-class-strategies';
import { AssetClass } from '../model/asset-class';
import { Portfolio } from '../model/portfolio';

import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';
import {FinancialData} from "../model/financial-data";
import {FinancialDataElement} from "../model/financial-data-element";
import {FinancialDataSet} from "../model/financial-data-set";
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';

@Injectable()
export class AssetService {
  private portfolio:any;
  private backtesting: any;
  private data:any = {};
  assetClassStrategies:AssetClassStrategies;
  assetClassStrategies2:AssetClassStrategies;
  financialDataSet:FinancialDataSet;
  forecastDataSet:FinancialDataSet;
  private capital = 10000;
  private assetClassSet:AssetClass[] = [];

  constructor(private AppService:AppService) { }

  getPortfolio(){
    return this.portfolio;
  }

  getDefaultAssetClass(){

    this.getAssetClassSet();

    return this.assetClassSet;
  }

  //REMAPPING ASSET CLASS
  getAssetClassSet() {
    return this.AppService.getAssetClassSet().map(res => this.assignAssetClass(res));
    //return this.AppService.getFinancialDataSetForAssetClass(id,period).map(res => this.assignFinancialData(res));
  }
  assignAssetClass(res) {
    this.assetClassSet = res;
    this.assetClassStrategies = new AssetClassStrategies();
    console.log("assetclass",this.assetClassStrategies);
    this.assetClassStrategies.createAssetClassStrategies(res);
    console.log("ASSETCLASSSTRATEGIES",this.assetClassStrategies.getAssetClassStrategies());
    this.assetClassStrategies2 = new AssetClassStrategies();
    console.log("assetclass2",this.assetClassStrategies2);
    //this.assetClassStrategies2.createAssetClassStrategies(res);
    console.log("ASSETCLASSSTRATEGIES2",this.assetClassStrategies2.getAssetClassStrategies());
    return this.assetClassStrategies;
  }

  //REMAPPING PORTFOLIO
  getPortfolioForPeriod(period) {
    return this.AppService.getPortfolioForPeriod(period).map(res => this.mapPortfolio(res));
  }
  //FINANCIAL DATASET
  getFinancialDataSet(period,type){
    return this.AppService.getFinancialDataSet(period)
        .map(financialDataSet => this.assignFinancialData(financialDataSet,type));
  }
  getForecast(period,type){
    return this.AppService.getForecast(period)
        .map(forecastDataSet => this.assignForecastData(forecastDataSet,type));
  }
  assignForecastData(forecastDataSet,type){
    this.forecastDataSet = new FinancialDataSet();
    this.forecastDataSet.createFinancialDataSet(forecastDataSet,type);
    return this.forecastDataSet.getFinancialDataSet();
  }
  assignFinancialData(financialDataSet,type){
    this.financialDataSet = new FinancialDataSet();
    this.financialDataSet.createFinancialDataSet(financialDataSet,type);
    return this.financialDataSet.getFinancialDataSet();
  }
  mapPortfolio(res){
    if (res.response == 1) {
      this.portfolio = new Portfolio(res.data);
      this.data = this.portfolio.getData();
    }

    return {response: res.response, data: this.data}
  }

  getBacktesting(list, period){
    return this.AppService.getBacktesting(list, period, this.capital).map(res => this.mapBacktesting(res));
  }
  mapBacktesting(res){
    if (res.response == 1) {
      this.backtesting = new Portfolio(res.data);
      this.data = this.backtesting.getData();
    }

    return {response: res.response, data: this.data}
  }
}
