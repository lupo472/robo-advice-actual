import { Injectable, Inject } from '@angular/core';
import { AppConfig } from './app.config';
import { AppService } from './app.service';
import { AssetClassStrategy } from '../model/asset-class-strategy';
import { AssetClassStrategies } from '../model/asset-class-strategies';
import { AssetClass } from '../model/asset-class';

import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class AssetService {
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
  assignFinancialData(res) {
    return res.data;
  }

  //REMAPPING PORTFOLIO
  getUserCurrentPortfolio(){
    return this.AppService.getUserCurrentPortfolio(18, 'a@a', 'aaaaa').map(res => this.assignPortfolio(res));
  }

  assignPortfolio(res){
    return res.data.list;
  }

}
