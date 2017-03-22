import { StrategyService } from '../../services/strategy.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-strategy-graph',
  templateUrl: './strategy-graph.component.html',
  styleUrls: ['./strategy-graph.component.scss']
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

      this.labels = data.labels;
      this.datasets = data.datasets;
      this.colors = data.colors;

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
