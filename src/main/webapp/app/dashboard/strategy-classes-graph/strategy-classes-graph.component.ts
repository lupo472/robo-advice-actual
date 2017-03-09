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
  public date: Array<any>;
  public data: Array<any>;

  public mainChartLabels = [];
  public mainChartData: Array<any> = [{},{}];

  ngOnInit() {
    this.user = this.UserService.getUser();
    this.AppService.getUserPortfolioPeriod(this.user.id, 30).subscribe(res => this.getPortfolio(res));
  }

  getPortfolio(res) {
    if (res.response == 1) {
      this.portfolio = res.data;

      var value = [];
      var percentage = [];

      var date = [];
      var data = [];

      this.portfolio.forEach((item, index) => {

        var portfolioElem = item.list;

        var label = [];
        var id = [];
        var assetClass = [];

        portfolioElem.forEach((element, i) => {
          assetClass[i] = element.assetClassStrategy.assetClass;

          var j = assetClass[i].id - 1;

          if (percentage[j] == undefined) {
            percentage[j] = [];
          }
          percentage[j].push(element.assetClassStrategy.percentage);

          if (value[j] == undefined) {
            value[j] = [];
          }
          value[j].push(element.value);

          data[j] = { data: value[j], label: assetClass[i].name };
        })

        date.push(item.date);
      })

      this.mainChartLabels = date;
      this.mainChartData = data;

      console.dir(this.mainChartData);
      console.dir(this.mainChartLabels);

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

  public mainChartOptions: any = {
    responsive: true,
    maintainAspectRatio: false,
    scales: {
      xAxes: [{
        gridLines: {
          drawOnChartArea: false,
        },
        ticks: {
          callback: function(value: any) {
            return value.charAt(0);
          }
        }
      }],
      yAxes: [{
        ticks: {
          beginAtZero: true,
          maxTicksLimit: 10,
          stepSize: Math.ceil(3000 / 10),
          max: 3000
        }
      }]
    },
    elements: {
      line: {
        borderWidth: 2
      },
      point: {
        radius: 0,
        hitRadius: 10,
        hoverRadius: 4,
        hoverBorderWidth: 3,
      }
    },
    legend: {
      display: false
    }
  };
  public mainChartColours: Array<any> = [
    { //brandInfo
      backgroundColor: this.convertHex(this.brandInfo, 10),
      borderColor: this.brandInfo,
      pointHoverBackgroundColor: '#fff'
    },
    { //brandSuccess
      backgroundColor: 'transparent',
      borderColor: this.brandSuccess,
      pointHoverBackgroundColor: '#fff'
    },
    { //brandDanger
      backgroundColor: 'transparent',
      borderColor: this.brandDanger,
      pointHoverBackgroundColor: '#fff',
      borderWidth: 1,
      borderDash: [8, 5]
    }
  ];
  public mainChartLegend: boolean = false;
  public mainChartType: string = 'line';
}
