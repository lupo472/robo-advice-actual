
import { Injectable, Inject } from '@angular/core';
import { AppConfig } from './app.config';
import { AppService } from './app.service';
import { AssetService } from './asset.service';
import { Strategy } from '../model/strategy';
import { DefaultStrategy } from '../model/default-strategy';
import { ExtendedDefaultStrategy } from '../model/extended-default-strategy';
import { AssetClassStrategy } from '../model/asset-class-strategy';
import { AssetClass } from '../model/asset-class';
import { Asset } from '../model/asset';
import { Cookie } from 'ng2-cookies';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class StrategyService {
  assetClassStrategy:AssetClassStrategy;
  defaultStrategy:DefaultStrategy;
  defaultStrategies:DefaultStrategy[];
  extendedDefaultStrategy:ExtendedDefaultStrategy;
  extendedDefaultStrategies:ExtendedDefaultStrategy[];
  assetClass:AssetClass;
  asset:Asset;
  strategies:Map<number, AssetClassStrategy> = new Map<number, AssetClassStrategy>();
  strategy:Strategy;
  sumPercentage:number;
  maxPercentage:number;
  oldValue:number;
  result:any;

  public strategySet = [];

  //isCustom:boolean;
  constructor(private AppService:AppService, private AssetService:AssetService) {
    this.sumPercentage = 0;
    this.maxPercentage = 100;
    this.strategies.set(1,new AssetClassStrategy(0,new AssetClass(1,"")));
    this.strategies.set(2,new AssetClassStrategy(0,new AssetClass(2,"")));
    this.strategies.set(3,new AssetClassStrategy(0,new AssetClass(3,"")));
    this.strategies.set(4,new AssetClassStrategy(0,new AssetClass(4,"")));
    //this.extendedDefaultStrategy = new ExtendedDefaultStrategy();
    this.oldValue = 0;
    this.defaultStrategies = [];
    this.extendedDefaultStrategies = [];
  }

  //SLIDER MAPPING
  createAssetClassStrategy(id,oldValue) {
    console.log("++onStrategy");
      // console.log("oldValue " + oldValue);
      if (this.strategies.get(id).percentage - oldValue + this.sumPercentage > 100) {
        if (this.maxPercentage !=0) {
          if (this.strategies.get(id).percentage > oldValue) {
            this.strategies.get(id).setPercentage(this.maxPercentage + oldValue);
          } else {
            this.strategies.get(id).setPercentage(this.maxPercentage);
          }
        } else {
          if (this.strategies.get(id).percentage > oldValue) {
              this.strategies.get(id).setPercentage(oldValue);
            }
        }
      }
      this.strategies.set(this.strategies.get(id).assetClass.id,this.strategies.get(id));
      var sum = 0;
      this.strategies.forEach( (k,v) => [
        sum += k.percentage
      ]);
      this.sumPercentage = sum;
      this.maxPercentage = 100 - this.sumPercentage;

      // console.log(this.strategies);
      // console.log("sumPercentage" + this.sumPercentage);
      // console.log("maxPercentage" + this.maxPercentage);
      return this.strategies.get(id).getPercentage();
  }

  setCustomStrategy() {
    this.strategy = new Strategy();
    this.strategy.setUserId(Cookie.get('id'));
    var array = [];
    this.strategies.forEach( (k,v) => [
      array.push(k)
    ]);
    this.strategy.setStrategyArray(array);
      return this.AppService.setCustomStrategy(this.strategy).map(res => console.log(res));
  }

  // STRATEGY JSON REMAPPING
  getDefaultStrategySet() {
    return this.AppService.getDefaultStrategySet().map(res => this.assignStrategy(res));
  }

  assignStrategy(res){
    // var arrayPercentage = [];
    // var arrayNull = [];
    // var arrayLabels = [];
    // var arrayColours = [];
    this.extendedDefaultStrategies = [];
    res.data.forEach((item, index) => {
      //this.defaultStrategy = new DefaultStrategy(item.name);
      this.extendedDefaultStrategy = new ExtendedDefaultStrategy(item.name);

      item.list.forEach((element, i) =>{
        let color = this.AssetService.assignColour(element.assetClass.id);
        this.asset = new Asset(element.percentage,
          new AssetClass(element.assetClass.id,element.assetClass.name));
        this.asset.color=color;
        //this.defaultStrategy.setList(element);
        this.extendedDefaultStrategy.setList(this.asset);

      //  arrayPercentage[i] = element.percentage;
      //  arrayNull[i] = 0;
      //  arrayLabels[i] = element.assetClass.name;
      //  arrayColours[i] = this.assignColour(element.assetClass.id);
      });
       //
      //   console.log("defaultstrategy");
      //   console.log(this.defaultStrategy);
        //this.defaultStrategies.push(this.defaultStrategy);
        this.extendedDefaultStrategies.push(this.extendedDefaultStrategy);

      // this.strategySet[index] = {
      //                           name:  item.name,
      //                           data: arrayPercentage,
      //                           labels: arrayLabels,
      //                           colours: [{backgroundColor: arrayColours, borderWidth: 3}]
      //                           }


      // console.log(this.strategySet[index].colours);
      //
      // arrayPercentage = [];
      // arrayLabels = [];
      // arrayColours = [];
    })
    //this.defaultStrategies.push(new DefaultStrategy("custom"));
    this.extendedDefaultStrategies.push(new ExtendedDefaultStrategy("custom"));
    // console.log(this.defaultStrategies);
    // console.log("extended");
    // console.log(this.extendedDefaultStrategies);
    // this.strategySet[res.data.length]={name: 'custom', data: arrayNull}
    return this.extendedDefaultStrategies;
    // return this.strategySet;
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
