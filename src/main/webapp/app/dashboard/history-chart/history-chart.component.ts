import { Component, OnInit } from '@angular/core';
import {StrategyService} from "../../services/strategy.service";

@Component({
  selector: 'app-history-chart',
  templateUrl: './history-chart.component.html',
  styleUrls: ['./history-chart.component.scss']
})
export class HistoryChartComponent implements OnInit {

  public period = 30;
  public startdate: Date;
  public maxdate: Date;
  public chartData = [];
  public labels = [];
  public dati = [];
  public render: boolean = false;
  public trendLabels= [];
  public options;

  id = "chartdiv";
  constructor(private StrategyService: StrategyService) { }

  ngOnInit() {
    this.StrategyService.getHistoryChart().subscribe(res => this.getStrategies(res));
  }

  refreshData() {
    this.dati = [];
    this.chartData = [];
    this.labels = [];
    let res = this.StrategyService.refreshHistory(this.startdate);
    this.getStrategies(res);
  }

  getStrategies(res) {
    if (res) {
      console.log("res#########", res);
      this.changeChart();
      this.options = res;
    }
    /* if (res) {
      //AGGIUNTA TREND LABELS
      this.chartData = res.data;
      this.labels = res.labels;
      this.trendLabels=this.StrategyService.createTrendLabelHistory(this.labels);
      this.labels.forEach((label,i)=>{
        let diff:number=this.trendLabels[i].endvalue-this.trendLabels[i].startvalue;
        console.log("diff", diff.toFixed(2));
        if(diff>0){
          this.labels[i] = label+ " ("+"+ $"+diff.toFixed(2)+" )";
        }
        else this.labels[i] = label+ " ($ "+diff.toFixed(2)+" )";
      });
      console.log("NEW LABELS" ,this.labels);
      this.refreshChart();
      this.render = true;
      console.log("history",this.StrategyService.historyStrategies);
    }*/

  }
  changeChart() {
    // Make a copy of the existing config
    //this.options = JSON.parse(JSON.stringify(this.options));
    this.render=true;

  }

}
