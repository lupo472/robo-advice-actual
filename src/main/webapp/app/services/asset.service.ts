import { Injectable, Inject } from '@angular/core';
import { AppConfig } from './app.config';
import { AppService } from './app.service';
import { AssetClassStrategy } from '../model/asset-class-strategy';
import { AssetClass } from '../model/asset-class';

import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class AssetService {

  constructor(private AppService:AppService) { }

  private portfolio:any;
  private dataset:any;
  private date:any;

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
      this.portfolio = res.data;

      let value = [];

      let percentage = [];
      let name = [];

      this.portfolio.forEach((item, index) => {

        let portfolioElem = item.list;

        let tendency;

        portfolioElem.forEach(element => {

          let j = element.assetClassStrategy.id - 1;

          if (value[j] == undefined) {
            value[j] = [];
          }

          value[j][index] = element.value;
          percentage[j] = element.assetClassStrategy.percentage;
          name[j] = element.assetClassStrategy.name;
          if(value[j][index] > value[j][index-1]){
            tendency = "positive";
          }else if(value[j][index] < value[j][index-1]) {
            tendency = "negative";
          }else{
            tendency = "equal";
          }

          this.dataset[j] = { data: value[j],
            label: name[j],
            percentage: percentage[j],
            value: value[j][index] ,
            tendency:tendency
          };
        });

        this.date.push(item.date);
      });

      for(let iter = 0; iter < this.dataset.length-1; iter++){
        console.log("Object: ",iter, this.dataset[iter]);
        if(this.dataset[iter] == undefined){
          console.log("splice");
          this.dataset.splice(iter, 1);
          iter = 0;
        }
      }
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
