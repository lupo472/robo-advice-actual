import {Component, OnInit, ViewChild} from '@angular/core';

import {AssetService} from "../services/asset.service";
import {PortfolioGraphComponent} from "./portfolio-graph/portfolio-graph.component";

@Component({
  templateUrl: 'dashboard.component.html'
})
export class DashboardComponent implements OnInit {

  @ViewChild('portfolioGraph') portfolioGraph;

  constructor(private AssetService:AssetService) { }

  public period:number = 0;

  public render: boolean = false;

  public data:any = {};

  ngOnInit() {
    this.AssetService.getPortfolioForPeriod(this.period).subscribe(res => this.getPortfolio(res));
  }

  getPortfolio(res) {
    if (res.response == 1) {
      this.data = res.data;
      console.log("DATA PORTFOLIO: ", this.data);
      this.render = true;

      this.portfolioGraph.refreshChart(this.data);
    }
  }
}
