import { StrategyService } from '../../services/strategy.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-strategy-graph',
  templateUrl: './strategy-graph.component.html'
})
export class StrategyGraphComponent implements OnInit {

  constructor(private StrategyService: StrategyService) { }

  public labels: Array<string> = [];
  public datasets: Array<number> = [];
  public date: string;
  public colors:Array<any> = [];

  public render: boolean = false;

  ngOnInit() {
    this.StrategyService.getActiveStrategy().subscribe(res => this.getStrategy(res));
  }

  getStrategy(data) {

    if(data) {
      let t = data.getChartData();
      this.labels = t.labels;
      this.datasets = t.datasets;
      this.colors = t.colors;

      this.render = true;
    }
  }

  //convert Hex to RGBA
  public convertHex(hex: string, opacity: number) {
    hex = hex.replace('#', '');
    let r = parseInt(hex.substring(0, 2), 16);
    let g = parseInt(hex.substring(2, 4), 16);
    let b = parseInt(hex.substring(4, 6), 16);

    let rgba = 'rgba(' + r + ',' + g + ',' + b + ',' + opacity / 100 + ')';
    return rgba;
  }

  // events
  public chartClicked(e: any): void {
    console.log(e);
  }

  public chartHovered(e: any): void {
    console.log(e);
  }


  // Pie

  public strategyOptions: any = {
    maintainAspectRatio: false,
    cutoutPercentage: 20,
    legend: {
      display: false
    }
  };
  public pieChartType: string = 'doughnut';

}
