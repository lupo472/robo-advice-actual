import { Component, OnInit,ViewChild, Renderer } from '@angular/core';
import {MyActiveStrategyAmChart} from "../model/my-active-strategy-am-chart";
import { StrategyService } from '../services/strategy.service';

@Component({
  templateUrl: 'dashboard.component.html'
})
export class DashboardComponent implements OnInit {
  options;
  render = false;
  @ViewChild('chartTest2') public chartTest2;

  constructor(private StrategyService:StrategyService) { }

  ngOnInit() {
    this.StrategyService.getActiveStrategy().subscribe(res => this.getStrategy(res));

  }
  getStrategy(data){
    this.options = data;
    this.render = true;
    console.log("data",data);
  }
  changeChart(){
    this.chartTest2.changeChart();
  }

}
