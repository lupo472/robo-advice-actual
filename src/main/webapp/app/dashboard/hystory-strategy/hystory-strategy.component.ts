import { Component, OnInit ,ViewChild} from '@angular/core';
import {BaseChartDirective} from "ng2-charts";
import {StrategyService} from "../../services/strategy.service";

@Component({
  selector: 'app-hystory-strategy',
  templateUrl: './hystory-strategy.component.html',
  styleUrls: ['./hystory-strategy.component.scss']
})
export class HystoryStrategyComponent implements OnInit {

  @ViewChild(BaseChartDirective) chart: BaseChartDirective;

  public strategies:any ={};
  public period = 30;
  public startdate: Date;
  public chartData=[];
  public dataclass=["bonds","forex","stocks","commodities"]; // da sostituire con il model
  public colorclass=["#4dbd74","#63c2de","#f8cb00","#f86c6b"]; // da sostituire con il model
  public labels=[];
  public dati=[];
  public render: boolean = false;


  ress={
    "response": 1,
    "data": [
      {
        "list": [
          {
            "id": 1,
            "name": "bonds",
            "percentage": 95
          },
          {
            "id": 4,
            "name": "commodities",
            "percentage": 5
          }
        ],
        "active": true,
        "date": "2017-03-11"
      },
      {
        "list": [
          {
            "id": 1,
            "name": "bonds",
            "percentage": 90
          },
          {
            "id": 4,
            "name": "commodities",
            "percentage": 10
          }
        ],
        "active": true,
        "date": "2017-02-10"
      },
      {
        "list": [
          {
            "id": 1,
            "name": "bonds",
            "percentage": 50
          },
          {
            "id": 4,
            "name": "commodities",
            "percentage": 50
          }
        ],
        "active": true,
        "date": "2017-02-01"
      },
      {
        "list": [
          {
            "id": 1,
            "name": "bonds",
            "percentage": 2
          },
          {
            "id": 2,
            "name": "forex",
            "percentage": 42
          },
          {
            "id": 3,
            "name": "stocks",
            "percentage": 50
          },
          {
            "id": 4,
            "name": "commodities",
            "percentage": 6
          }
        ],
        "active": false,
        "date": "2016-11-10"
      }
    ]
  };

  constructor( private StrategyService: StrategyService) {
    this.startdate=new Date();
    this.startdate.setDate(this.startdate.getDate() - this.period);
    console.log("this.startdate: "+this.startdate);
  }

  ngOnInit() {
    this.StrategyService.getHistoryStrategies().subscribe(res=>this.getStrategies(res));
    // this.strategies = this.ress;
    // this.getStrategies();
  }

  refreshData(){
    this.dati=[];
    this.chartData=[];
    this.labels=[];
    let res=this.StrategyService.refreshHistory(this.startdate);
    console.log("res refresh", res);
    this.getStrategies(res);
  }

  getStrategies(res){

    this.chartData=res.data;
    this.labels=res.labels;
    console.log("this.chartData: ",this.chartData);
    console.log("this.labels :",this.labels);
    this.refreshChart();
    this.render = true;

  }

  refreshChart() {
    setTimeout(() => {

      if (this.chart.chart.config.data) {
        this.chart.chart.config.data.labels = this.labels;
        this.chart.chart.config.data.datasets = this.chartData;
        this.chart.chart.update();
      }
    });
  }
  // barChart
  public barChartOptions:any = {
    scaleShowVerticalLines: false,
    responsive: true
  };

  public barChartType:string = 'bar';
  public barChartLegend:boolean = true;
/*  public barChartData:any[] = this.chartData;
  public barChartLabels:string[] = this.labels;*/


}
