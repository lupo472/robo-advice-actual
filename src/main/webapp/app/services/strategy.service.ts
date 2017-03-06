
import { Injectable, Inject } from '@angular/core';
import { AppConfig } from './app.config';

import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class StrategyService {
  strategies:Map<number, number> = new Map<number, number>();;
  sumPercentage:number;
  maxPercentage:number;
  prova:any;
  oldValue:number;
  constructor(private http:Http) {
    this.sumPercentage = 0;
    this.maxPercentage = 100;
    this.strategies.set(1,0);
    this.strategies.set(2,0);
    this.strategies.set(3,0);
    this.strategies.set(4,0);
    this.oldValue = 0;
  }
  // getStrategy(){
  //   return this.strategies;
  // }

  onStrategy(strategy:any){
    console.log("++onStrategy");
      this.oldValue = this.strategies.get(strategy.id_asset);
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
      this.strategies.set(strategy.id_asset,strategy.percentage);
      var sum = 0;
      this.strategies.forEach( (k,v) => [
        sum += k
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
}
