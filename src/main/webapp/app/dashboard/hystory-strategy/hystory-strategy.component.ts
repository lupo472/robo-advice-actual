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
    //this.getStrategies();
    //this.chart.chart.update();
  }

  getStrategies(res){
    this.strategies=res;
    console.log("strategies: ",this.strategies);
    if(this.strategies.response == 1 ){
      this.strategies.data.forEach((strategy,i)=>{
        var beginning = new Date(strategy.date);
        console.log("this.startdate :"+this.startdate);
        if(beginning>=this.startdate){
          console.log("strategia compresa nell'intervallo, date:"+beginning);
          this.labels.push(strategy.date);
          strategy.list.forEach((classe,j)=>{
            var idClasse=classe.id;
            if(this.dati[idClasse-1]==undefined){
              this.dati[idClasse-1]=new Array(this.strategies.data.length);
            }
            this.dati[idClasse-1][i]=classe.percentage;
          })
        }
      })
      for(var k=0;k<this.dataclass.length;k++){
        if(this.dati[k]==undefined){
          this.dati[k]=new Array(this.labels.length);
        }
      }

    }
    console.log("PRIMA DEGLI ZERI ",this.dati);
    //INSERIMENTO ZERI
    for(var i=0;i<this.dati.length;i++){
      for(var j=0;j<this.dati[i].length;j++){
        if(this.dati[i][j]==undefined){
          this.dati[i][j]=0;
        }
      }
    }console.log("DOPO GLI ZERI ",this.dati);

    for(var i=0;i<this.dati.length;i++){
      this.chartData.push({data: this.dati[i], label:this.dataclass[i], backgroundColor: this.colorclass[i]});
    }
    console.log("chartData: ",this.chartData);

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
  public barChartLabels:string[] = this.labels;
  public barChartType:string = 'bar';
  public barChartLegend:boolean = true;
  public barChartData:any[] = this.chartData;


}
