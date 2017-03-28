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
  private forecasting: any;
  private data:any = {};
  assetClassStrategies:AssetClassStrategies;
  financialDataSet:FinancialDataSet;
  forecastDataSet:FinancialDataSet;
  private capital = 10000;
  private assetClassSet:AssetClass[] = [];

  constructor(private AppService:AppService) { }

  getPortfolio(){
    return this.portfolio;
  }

  getDefaultAssetClass(){
    return this.AppService.getAssetClassSet().map(res => this.mapAssetClass(res));
  }
  mapAssetClass(res){
    this.assetClassSet = res;
    return this.assetClassSet;
  }

  //REMAPPING ASSET CLASS
  getAssetClassSet() {
    return this.AppService.getAssetClassSet().map(res => this.assignAssetClass(res));
  }
  assignAssetClass(res) {
    this.assetClassStrategies = new AssetClassStrategies();
    this.assetClassStrategies.createAssetClassStrategies(res);
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
    }

    return {response: res.response, data: this.portfolio.getData()}
  }

  getBacktesting(list, period){
    console.log("LIST SENT",list);
    return this.AppService.getBacktesting(list, period, this.capital).map(res => this.mapBacktesting(res));
  }
  mapBacktesting(res){
    if (res.response == 1) {
      this.backtesting = new Portfolio(res.data);
    }

    return {response: res.response, data: this.backtesting.getData()}
  }

  getForecasting(period){
    return this.AppService.getDemo(period).map(res => this.mapForecasting(res));
  }
  mapForecasting(res){
    if (res.response == 1) {
      this.forecasting = new Portfolio(res.data);
    }

    return {response: res.response, data: this.forecasting.getData()}
  }
}
