import {Component, OnInit, ViewChild} from '@angular/core';
import { AssetService } from "../services/asset.service";
import { AssetClassStrategy } from "../model/asset-class-strategy";
import { AssetClass } from "../model/asset-class";
import {mockAssetClassStrategy} from "../mocks/asset-class-strategy-mock";

@Component({
  templateUrl: 'backtesting.component.html'
})
export class BacktestingComponent implements OnInit {

  @ViewChild('backtestingGraph') backtestingGraph;

  constructor(private AssetService:AssetService) { }

  private list: AssetClassStrategy[] = [];
  private period:number = 30;

  public data:any = {};

  public render: boolean = false;
  public start: boolean = false;

  public max: number = 100;

  public selected:any[] = [];
  public assetclasses:AssetClass[] = [];

  public sum:number = 0;

  ngOnInit() {
    this.AssetService.getDefaultAssetClass().subscribe(res => this.setAssetClasses(res));
  }

  setAssetClasses(res){
    this.assetclasses = res;
  }

  createGraph(){

    this.assetclasses.forEach((item, index)=>{
      let assetClassStrategy = new AssetClassStrategy(this.selected[index], item.id, item.name);
      this.list.push(assetClassStrategy);
    });

    this.selected = [];

    console.log("STRATEGY: ", this.list);

    this.AssetService.getBacktesting(this.list, this.period).subscribe(res => this.getBacktesting(res));
  }

  getBacktesting(res) {
    if (res.response == 1) {
      this.data = res.data;

      console.log("DATA FOR BACKTEST: ", res.data);

      this.render = true;
      this.backtestingGraph.refreshChart(this.data);
    }
  }

  updateSum(){
    this.sum = 0;

    this.assetclasses.forEach((item, index)=>{
      if(this.selected[index] == undefined){
        this.selected[index] = 0;
      }else if((this.sum + this.selected[index]) <= 100) {
        this.sum = this.sum + this.selected[index];
      }
    });

    this.start = this.sum == 100;
  }
}
