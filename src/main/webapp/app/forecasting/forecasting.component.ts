import {Component, OnInit, ViewChild} from '@angular/core';
import { AssetService } from "../services/asset.service";
import { AssetClassStrategy } from "../model/asset-class-strategy";
import { AssetClass } from "../model/asset-class";
import {mockAssetClassStrategy} from "../mocks/asset-class-strategy-mock";

@Component({
  templateUrl: 'forecasting.component.html'
})
export class ForecastingComponent implements OnInit {

  @ViewChild('forecastingGraph') forecastingGraph;

  constructor(private AssetService:AssetService) { }

  private period:number = 180;

  public data:any = {};

  public render: boolean = false;
  public start: boolean = false;

  public max: number = 100;

  public selected:any[] = [];
  public assetclasses:AssetClass[] = [];

  public sum:number = 0;

  ngOnInit() {
    this.AssetService.getForecasting(this.period).subscribe(res => this.getForecasting(res));
  }

  getForecasting(res) {
    if (res.response == 1) {
      this.data = res.data;

      console.log("DATA FOR BACKTEST: ", res.data);

      this.render = true;
      this.forecastingGraph.refreshChart(this.data);
    }
  }
}
