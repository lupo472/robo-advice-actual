import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-strategy-classes-graph',
  templateUrl: './strategy-classes-graph.component.html',
  styleUrls: ['./strategy-classes-graph.component.scss']
})
export class StrategyClassesGraphComponent implements OnInit {

  constructor() { }

  public brandPrimary:string =  '#20a8d8';
  public brandSuccess:string =  '#4dbd74';
  public brandInfo:string =   '#63c2de';
  public brandWarning:string =  '#f8cb00';
  public brandDanger:string =   '#f86c6b';

  //convert Hex to RGBA
  public convertHex(hex:string,opacity:number){
    hex = hex.replace('#','');
    let r = parseInt(hex.substring(0,2), 16);
    let g = parseInt(hex.substring(2,4), 16);
    let b = parseInt(hex.substring(4,6), 16);

    let rgba = 'rgba('+r+','+g+','+b+','+opacity/100+')';
    return rgba;
  }

  // events
  public chartClicked(e:any):void {
    console.log(e);
  }

  public chartHovered(e:any):void {
    console.log(e);
  }

  public random(min:number, max:number) {
    return Math.floor(Math.random()*(max-min+1)+min);
  }

  public mainChartElements:number = 7;
  public mainChartData1:Array<number> = [];
  public mainChartData2:Array<number> = [];
  public mainChartData3:Array<number> = [];
  public mainChartData4:Array<number> = [];

  public mainChartData:Array<any> = [
    {
      data: this.mainChartData1,
      label: 'Bonds'
    },
    {
      data: this.mainChartData2,
      label: 'Stocks'
    },
    {
      data: this.mainChartData3,
      label: 'Forex'
    },
    {
      data: this.mainChartData4,
      label: 'Commodities'
    }
  ];
  public mainChartLabels:Array<any> = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];
  public mainChartOptions:any = {
    responsive: true,
    maintainAspectRatio: false,
    scales: {
      xAxes: [{
        gridLines: {
          drawOnChartArea: false,
        },
        ticks: {
          callback: function(value:any) {
            return value.charAt(0);
          }
        }
      }],
      yAxes: [{
        ticks: {
          beginAtZero: true,
          maxTicksLimit: 5,
          stepSize: Math.ceil(100 / 5),
          max: 100
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
  public mainChartColours:Array<any> = [
    { //brandInfo
      backgroundColor: this.convertHex(this.brandInfo,10),
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
  public mainChartLegend:boolean = false;
  public mainChartType:string = 'line';

  ngOnInit() {
    //generate random values for mainChart
    for (var i = 0; i <= this.mainChartElements; i++) {
      this.mainChartData1.push(this.random(10,100));
      this.mainChartData2.push(this.random(10,100));
      this.mainChartData3.push(this.random(10,100));
      this.mainChartData4.push(this.random(10,100));
    }
  }

}
