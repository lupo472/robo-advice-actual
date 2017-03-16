import { Injectable, Inject } from '@angular/core';
import { AppConfig } from './app.config';
import { AppService } from './app.service';
import { AssetClassStrategy } from '../model/asset-class-strategy';
import { AssetClassStrategies } from '../model/asset-class-strategies';
import { AssetClass } from '../model/asset-class';
import { Portfolio } from '../model/portfolio';

import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class AssetService {
  private portfolio:any;
  private data:any = {};
  assetClassStrategies:AssetClassStrategies;
  constructor(private AppService:AppService) {
  }

  //REMAPPING ASSET CLASS
  getAssetClassSet() {
    return this.AppService.getAssetClassSet().map(res => this.assignAssetClass(res));
    //return this.AppService.getFinancialDataSetForAssetClass(id,period).map(res => this.assignFinancialData(res));
  }
  assignAssetClass(res) {
    this.assetClassStrategies = new AssetClassStrategies();
    this.assetClassStrategies.createAssetClassStrategies(res.data);
    console.log("ASSETCLASSSTRATEGIES",this.assetClassStrategies.getAssetClassStrategies());
    return this.assetClassStrategies;
  }

  //REMAPPING PORTFOLIO
  getPortfolioForPeriod(period) {
    return this.AppService.getPortfolioForPeriod(period).map(res => this.mapPortfolio(res));
  }

  mapPortfolio(res){
    if (res.response == 1) {
      this.portfolio = new Portfolio(res.data);
      this.data = this.portfolio.getData();
    }

    return {response: res.response, data: this.data}
  }

}
