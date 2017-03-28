import { Component, OnInit } from '@angular/core';
import { AssetService } from "../services/asset.service";
import { AssetClassStrategy } from "../model/asset-class-strategy";
import { AssetClass } from "../model/asset-class";

@Component({
  templateUrl: 'backtesting.component.html'
})
export class BacktestingComponent implements OnInit {

  constructor(private AssetService:AssetService) { }

  private list: AssetClassStrategy[] = [new AssetClassStrategy(10, 1, "bonds"), new AssetClassStrategy(90, 2, "forex")];
  private period:number = 30;

  public data:any = {};

  public render: boolean = false;

  public selected = [];
  public assetclasses:AssetClass[] = [];

  public max = 100;


  ngOnInit() {
    this.AssetService.getBacktesting(this.list, this.period).subscribe(res => this.getBacktesting(res));
    this.assetclasses = this.AssetService.getDefaultAssetClass();
  }

  getBacktesting(res) {
    if (res.response == 1) {
      this.data = res.data;

      console.log("DATA FOR BACKTEST: ", this.data);

      this.render = true;
    }
  }
}
