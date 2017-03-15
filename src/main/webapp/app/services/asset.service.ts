import { Injectable, Inject } from '@angular/core';
import { AppConfig } from './app.config';
import { AppService } from './app.service';
import { AssetClassStrategy } from '../model/asset-class-strategy';
import { Portfolio } from '../model/portfolio';

import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class AssetService {

  constructor(private AppService:AppService) { }

  private portfolio:any;
  private dataset = [];
  private date = [];

  //REMAPPING ASSET CLASS
  getAssetClassSet() {
    return this.AppService.getAssetClassSet().map(res => this.assignAssetClass(res));
    //return this.AppService.getFinancialDataSetForAssetClass(id,period).map(res => this.assignFinancialData(res));
  }
  assignAssetClass(res) {
    let assetClassesStrategies = [];
    res.data.forEach((item,i) => {
      let assetClassStrategy = new AssetClassStrategy(0,item.id,item.name);
      assetClassesStrategies.push(assetClassStrategy);
    });
    return assetClassesStrategies;
  }
  assignFinancialData(res) {
    return res.data;
  }

  //REMAPPING PORTFOLIO
  getPortfolioForPeriod(period) {
    return this.AppService.getPortfolioForPeriod(period).map(res => this.mapPortfolio(res));
  }

  mapPortfolio(res){
    if (res.response == 1) {
      this.portfolio = new Portfolio(res.data);
      this.dataset = this.portfolio.getDataset();
      this.date = this.portfolio.getDate();
    }

    console.log("dataset: ", this.dataset);
    console.log("date: ", this.date);

    return {response: res.response, dataset: this.dataset, date: this.date}
  }

  //ASSIGN COLOUR
  assignColour(id){
    switch(id){
        case 1: return "#4dbd74";
        case 2: return "#63c2de";
        case 3: return "#f8cb00";
        case 4: return "#f86c6b";
        }
  }
}
