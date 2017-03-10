import { AppService } from '../../services/app.service';
import { UserService } from '../../services/user.service';
import { Component, OnInit, Input, KeyValueChangeRecord } from '@angular/core';

@Component({
  selector: 'app-strategy-classes-graph',
  templateUrl: './strategy-classes-graph.component.html',
  styleUrls: ['./strategy-classes-graph.component.scss']
})
export class StrategyClassesGraphComponent implements OnInit {

  constructor(private AppService: AppService, private UserService: UserService) { }

  user: any;
  public portfolio = [];

  public dataset = [];
  public date = [];

  public render: boolean = false;
  public response:string = 'Data not yet available';

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

      this.portfolio.forEach((item, index) => {

        var portfolioElem = item.list;

        var label = [];
        var id = [];
        var assetClass = [];

        portfolioElem.forEach((element, i) => {
          assetClass[i] = element.assetClassStrategy.assetClass;

          var j = assetClass[i].id - 1;

          if (value[j] == undefined) {
            value[j] = [];
          }

          value[j][index] = element.value;
          percentage[j] = element.assetClassStrategy.percentage;
          name[j] = assetClass[i].name;

          this.dataset[j] = { data: value[j], label: name[j], percentage: percentage[j], value: value[j][index] };
        })
        
        this.date.push(item.date);
      })
      
      for(var iter = 0; iter < this.dataset.length-1; iter++){
          console.log("Object: ",iter, this.dataset[iter]);
          if(this.dataset[iter] == undefined){
            console.log("splice");
            this.dataset.splice(iter, 1);
            iter = 0;
          }
        }

    this.render = true;

    }else{
      this.response = res.data;
    } 

    console.log("dataset: ", this.dataset);
    console.log("date: ", this.date);
  }

  public lineChartLabels = this.date;
  public lineChartData = this.dataset;

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
    { // ASSETCLASS1
      backgroundColor: this.convertHex('#4dbd74', 30),
      borderColor: '#4dbd74',
      pointBackgroundColor: '#4dbd74',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    },
    { //ASSETCLASS2
      backgroundColor: this.convertHex('#63c2de', 30),
      borderColor: '#63c2de',
      pointBackgroundColor: '#63c2de',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(77,83,96,1)'
    },
    { //ASSETCLASS3
      backgroundColor: this.convertHex('#f8cb00', 30),
      borderColor: '#f8cb00',
      pointBackgroundColor: '#f8cb00',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    },
    { //ASSETCLASS4
      backgroundColor: this.convertHex('#f86c6b', 30),
      borderColor: '#f86c6b',
      pointBackgroundColor: '#f86c6b',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(77,83,96,1)'
    }
  ];
  public lineChartLegend: boolean = true;
  public lineChartType: string = 'line';
}
