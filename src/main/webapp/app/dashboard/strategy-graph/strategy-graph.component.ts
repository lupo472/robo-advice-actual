import { Component, OnInit,Input } from '@angular/core';
import {Strategy} from "../../model/strategy";

@Component({
  selector: 'app-strategy-graph',
  templateUrl: './strategy-graph.component.html',
  styleUrls: ['./strategy-graph.component.scss']
})
export class StrategyGraphComponent implements OnInit {
  @Input() strategy: Strategy;
  @Input() isActive;
  constructor() { }

  ngOnInit() {
    this.strategy.resetArray();
    this.strategy.createChart();
  console.log("active inside",this.strategy);
  }

  //GENERAL SETTINGS
  public strategyOptions: any = {
    maintainAspectRatio: false,
    cutoutPercentage: 20,
    legend: {
      display: false
    }
  };
  //GRAPH TYPE
  public strategyType: string = 'pie';

}
