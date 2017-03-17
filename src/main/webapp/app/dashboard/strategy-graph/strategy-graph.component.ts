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

  public render: boolean = false;

  ngOnInit() {

    this.StrategyService.getHistoryStrategies().subscribe(res => this.getStrategy(res));
  }

  getStrategy(res) {

    let index = res.data.length - 1;
    let i = res.labels.length -1;

    this.datasets = [res.data[index]];
    console.log("STRATEGTDATA:", this.datasets);
    this.labels = res.labels;
    console.log("STRATEGTLABELS:", this.labels);
    this.date = res.labels[index];
    console.log("STRATEGTDATE:", this.date);

    this.render = true;

  }

  public brandPrimary: string = '#20a8d8';
  public brandSuccess: string = '#4dbd74';
  public brandInfo: string = '#63c2de';
  public brandWarning: string = '#f8cb00';
  public brandDanger: string = '#f86c6b';

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
  public colors = [
    {
      backgroundColor: [this.brandSuccess,
      this.brandInfo,
      this.brandWarning,
      this.brandDanger], borderWidth: 3
    }];
  public strategyOptions: any = {
    maintainAspectRatio: false,
    cutoutPercentage: 20,
    legend: {
      display: false
    }
  };
  public pieChartType: string = 'doughnut';

}
