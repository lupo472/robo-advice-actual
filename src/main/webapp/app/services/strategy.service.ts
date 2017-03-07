
import { Injectable, Inject } from '@angular/core';
import { AppConfig } from './app.config';
import { Strategy } from '../model/strategy';
import { AssetClass } from '../model/asset-class';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class StrategyService {
  strategies:Map<number, Strategy> = new Map<number, Strategy>();
  sumPercentage:number;
  maxPercentage:number;
  prova:any;
  oldValue:number;
  oldStrategy:any;
  //isCustom:boolean;
  constructor(private http:Http) {

    this.sumPercentage = 0;
    this.maxPercentage = 100;
    this.strategies.set(1,new Strategy(0,new AssetClass(1,"")));
    this.strategies.set(2,new Strategy(0,new AssetClass(2,"")));
    this.strategies.set(3,new Strategy(0,new AssetClass(3,"")));
    this.strategies.set(4,new Strategy(0,new AssetClass(4,"")));
    this.oldValue = 0;
    //this.isCustom = true;

  }
  // getStrategy(){
  //   return this.strategies;
  // }
  // setIsCustom(isCustom:boolean) {
  //   this.isCustom = isCustom;
  // }
  onStrategy(strategy:Strategy) {
    console.log("++onStrategy");
      this.oldStrategy = this.strategies.get(strategy.assetClass.id);
      this.oldValue = this.oldStrategy.percentage;
      console.log("oldValue " + this.oldValue);
      if (strategy.percentage - this.oldValue + this.sumPercentage > 100) {
        if (this.maxPercentage !=0) {
          if (strategy.percentage > this.oldValue) {
            strategy.percentage = this.maxPercentage + this.oldValue;
          } else {
            strategy.percentage = this.maxPercentage;
          }
        } else {
          if (strategy.percentage > this.oldValue) {
              strategy.percentage = this.oldValue;
            }
        }
      }
      this.strategies.set(strategy.assetClass.id,strategy);
      var sum = 0;
      this.strategies.forEach( (k,v) => [
        sum += k.percentage
      ]);
      this.sumPercentage = sum;
      this.maxPercentage = 100 - this.sumPercentage;

      console.log("sumPercentage" + this.sumPercentage);
      console.log("maxPercentage" + this.maxPercentage);
  }


  getDefaultStrategySet() {
    return this.http.post(AppConfig.url + 'getDefaultStrategySet', {})
      .map(response => response.json());
  }
  // getAssetClassSet(){
  //
  // }
}
