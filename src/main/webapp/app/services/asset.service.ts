import { Injectable, Inject } from '@angular/core';
import { AppConfig } from './app.config';
import { AppService } from './app.service';
import { Asset } from '../model/asset'

import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class AssetService {

  assetClass:Asset;
  assetClassSet:Asset[];

  constructor(private AppService:AppService) {
    this.assetClassSet = [];
  }

  //REMAPPING ASSET CLASS
  getAssetClassSet() {
    return this.AppService.getAssetClassSet().map(res => this.assignAssetClass(res));
  }

  assignAssetClass(res){
    // console.log("assignAssetClass");
    // console.log(res.data);
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
