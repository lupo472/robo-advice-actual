import { AppService } from '../../services/app.service';
import { UserService } from '../../services/user.service';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-strategy-classes-graph',
  templateUrl: './strategy-classes-graph.component.html',
  styleUrls: ['./strategy-classes-graph.component.scss']
})
export class StrategyClassesGraphComponent implements OnInit {

  constructor(private AppService: AppService, private UserService: UserService) { }

  user: any;
  public portfolio = [];

  public lineChartLabels = [];
  public lineChartData = [{}, {}, {}, {}];

  public data = [];

  ngOnInit() {
    this.user = this.UserService.getUser();
    this.AppService.getUserPortfolioPeriod(this.user.id, 30).subscribe(res => this.getPortfolio(res));
  }

  getPortfolio(res) {
    if (res.response == 1) {
      this.portfolio = res.data;

      var value = [];

      var percentage = [];
      var name = [];
      var date = [];

      this.portfolio.forEach((item, index) => {

        var portfolioElem = item.list;

        var label = [];
        var id = [];
        var assetClass = [];

        for (var k = 0; k < 4; k++) {

          if (value[k] == undefined) {
            value[k] = [];
          }
          
          value[k][index] = 0;
              percentage[k] = 0;
              name[k] = '';

          portfolioElem.forEach((element, i) => {
            assetClass[i] = element.assetClassStrategy.assetClass;

            var j = assetClass[i].id - 1;

            if (k == j) {
              value[k][index] = element.value;
              percentage[k] = element.assetClassStrategy.percentage;
              name[k] = assetClass[i].name;
            }
          })
          
          this.data[k] = { data: value[k], label: name[k], percentage: percentage[k] };
        }
        date.push(item.date);
      })

      this.lineChartLabels = date;
      this.lineChartData = this.data;

      console.dir(this.lineChartData);
      console.dir(this.lineChartLabels);

    } else {
      alert(res.data);
    }
  }

  //COLORS
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

  }

  public chartHovered(e: any): void {
    console.log(e);
  }

  // lineChart
  public lineChartOptions: any = {
    animation: false,
    responsive: true
  };
  public lineChartColours: Array<any> = [
    { // grey
      backgroundColor: 'rgba(148,159,177,0.2)',
      borderColor: 'rgba(148,159,177,1)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    },
    { // dark grey
      backgroundColor: 'rgba(77,83,96,0.2)',
      borderColor: 'rgba(77,83,96,1)',
      pointBackgroundColor: 'rgba(77,83,96,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(77,83,96,1)'
    },
    { // grey
      backgroundColor: 'rgba(148,159,177,0.2)',
      borderColor: 'rgba(148,159,177,1)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    }
  ];
  public lineChartLegend: boolean = false;
  public lineChartType: string = 'line';
}
