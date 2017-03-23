import { Component, OnInit,ViewChild, Renderer } from '@angular/core';
import {MyActiveStrategyAmChart} from "../model/my-active-strategy-am-chart";
import { StrategyService } from '../services/strategy.service';

@Component({
  templateUrl: 'dashboard.component.html'
})
export class DashboardComponent implements OnInit {
  public myActiveStrategyChart:MyActiveStrategyAmChart = new MyActiveStrategyAmChart("#fdd400");
  @ViewChild('chartTest2') public chartTest2;

  constructor(private StrategyService:StrategyService) { }

  ngOnInit() {
    this.StrategyService.getActiveStrategy().subscribe(res => this.getStrategy(res));

  }
  getStrategy(data){
    console.log("data",data);
  }
  changeChart(){
    this.chartTest2.changeChart();
  }

}
