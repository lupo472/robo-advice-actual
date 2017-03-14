import { Injectable, Inject } from '@angular/core';
import { AppConfig } from './app.config';
import { AppService } from './app.service';
import { AssetClassStrategy } from '../model/asset-class-strategy';
import { AssetClass } from '../model/asset-class';

import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class AssetService {

  constructor(private AppService:AppService) {
  }
  //REMAPPING ASSET CLASS
  getAssetClassSet() {
    return this.AppService.getAssetClassSet().map(res => this.assignAssetClass(res));
    //return this.AppService.getFinancialDataSetForAssetClass(id,period).map(res => this.assignFinancialData(res));
  }
  assignAssetClass(res) {
    let assetClassesStrategies = [];
    res.data.forEach((item,i) => {
      let assetClassStrategy = new AssetClassStrategy(0,new AssetClass(item.id,item.name));
      assetClassesStrategies.push(assetClassStrategy);
    });
    return assetClassesStrategies;
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
