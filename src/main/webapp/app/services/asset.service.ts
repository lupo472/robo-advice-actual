import { Injectable, Inject } from '@angular/core';
import { AppConfig } from './app.config';
import { AppService } from './app.service';

import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class AssetService {
  
  result = {"idUser":18,
            "list":[{"asset":{"id":1,"assetClass":{"id":1,"name":"bonds"},"name":"U.S. Treasury Bond Futures","dataSource":"CHRIS/CME_US1","percentage":80},"units":50.4983,"value":7598.4186},
                    {"asset":{"id":2,"assetClass":{"id":1,"name":"bonds"},"name":"Ultra U.S. Treasury Bond Futures","dataSource":"CHRIS/CME_UL1","percentage":20},"units":11.8959,"value":1897.0249},
                    {"asset":{"id":10,"assetClass":{"id":4,"name":"commodities"},"name":"Gold","dataSource":"COM/WLD_GOLD","percentage":30},"units":0.1215,"value":149.9553},
                    {"asset":{"id":11,"assetClass":{"id":4,"name":"commodities"},"name":"Silver","dataSource":"COM/WLD_SILVER","percentage":20},"units":5.5763,"value":99.9998},
                    {"asset":{"id":12,"assetClass":{"id":4,"name":"commodities"},"name":"Crude Oil","dataSource":"COM/OIL_BRENT","percentage":30},"units":2.7184,"value":150.0013},
                    {"asset":{"id":13,"assetClass":{"id":4,"name":"commodities"},"name":"Rice","dataSource":"COM/WLD_RICE_05","percentage":20},"units":0.2725,"value":100.0075}],
            "date":"2017-03-07"}
  

  constructor(private AppService:AppService) { }

  //REMAPPING ASSET CLASS
  getAssetClassSet() {
    return this.AppService.getAssetClassSet().map(res => this.assignAssetClass(res));
  }
  
  assignAssetClass(res){
    console.log("ASSET CLASS: " + JSON.stringify(res.data));
    return res.data;
  }

  //REMAPPING ASSET
  getAssetSet(){
    return this.AppService.getAssetSet().map(res => this.assignAsset(res));
  }
  
  assignAsset(res){
    console.log("ASSET: " + JSON.stringify(res.data));
    return res.data;
  }
  
  //REMAPPING PORTFOLIO
  getUserCurrentPortfolio(){
    return this.AppService.getUserCurrentPortfolio(18, 'a@a', 'aaaaa').map(res => this.assignPortfolio(res));
  }
  
  assignPortfolio(res){
    console.log("PORTFOLIO: " + JSON.stringify(res.data));
    
    return res.data.list;
  }
}
