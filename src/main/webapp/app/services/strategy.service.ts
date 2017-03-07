
import { Injectable, Inject } from '@angular/core';
import { AppConfig } from './app.config';
import { AppService } from './app.service';
import { AssetClassStrategy } from '../model/asset-class-strategy';
import { AssetClass } from '../model/asset-class';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class StrategyService {
  assetClassStrategy:AssetClassStrategy;
  assetClass:AssetClass;
  strategies:Map<number, AssetClassStrategy> = new Map<number, AssetClassStrategy>();
  sumPercentage:number;
  maxPercentage:number;
  prova:any;
  oldValue:number;
  oldStrategy:any = [{percentage:0}];
  result:any;

  public strategySet = [];

  //isCustom:boolean;
  constructor(private AppService:AppService) {
    this.sumPercentage = 0;
    this.maxPercentage = 100;
    this.strategies.set(1,new AssetClassStrategy(0,new AssetClass(1,"")));
    this.strategies.set(2,new AssetClassStrategy(0,new AssetClass(2,"")));
    this.strategies.set(3,new AssetClassStrategy(0,new AssetClass(3,"")));
    this.strategies.set(4,new AssetClassStrategy(0,new AssetClass(4,"")));
    this.oldValue = 0;
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
      //console.log(assetClassStrategy.getAssetClass());
      this.strategies.set(this.strategies.get(id).assetClass.id,this.strategies.get(id));
      var sum = 0;
      this.strategies.forEach( (k,v) => [
        sum += k.percentage
      ]);
      this.sumPercentage = sum;
      this.maxPercentage = 100 - this.sumPercentage;

      console.log(this.strategies);
      console.log("sumPercentage" + this.sumPercentage);
      console.log("maxPercentage" + this.maxPercentage);
      return this.strategies.get(id).getPercentage();
  }

  // STRATEGY JSON REMAPPING
  getDefaultStrategySet() {
    return this.AppService.getDefaultStrategySet().map(res => this.assignStrategy(res));
  }

  assignStrategy(res){

    var arrayPercentage = [];
    var arrayNull = [];
    var arrayLabels = [];
    var arrayColours = [];

    res.data.forEach((item, index) => {

      item.list.forEach((element, i) =>{
       arrayPercentage[i] = element.percentage;
       arrayNull[i] = 0;
       arrayLabels[i] = element.assetClass.name;
       arrayColours[i] = this.assignColour(element.assetClass.id);
        });

      this.strategySet[index] = {
                                name:  item.name,
                                data: arrayPercentage,
                                labels: arrayLabels,
                                colours: [{backgroundColor: arrayColours, borderWidth: 3}]
                                }


      console.log(this.strategySet[index].colours);

      arrayPercentage = [];
      arrayLabels = [];
      arrayColours = [];
    })

    this.strategySet[res.data.length]={name: 'custom', data: arrayNull}

    return this.strategySet;
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
