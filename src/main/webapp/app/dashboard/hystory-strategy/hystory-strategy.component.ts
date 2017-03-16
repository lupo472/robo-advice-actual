import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-hystory-strategy',
  templateUrl: './hystory-strategy.component.html',
  styleUrls: ['./hystory-strategy.component.scss']
})
export class HystoryStrategyComponent implements OnInit {

  strategies:any;
  chartData=[];
  numOfClasses=4;

  res={
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
            "percentage": 56
          }
        ],
        "active": false,
        "date": "2017-03-10"
      }
    ]
  };

  dataset=[];
  labels=[];
  dataclass=[];
  dati=[];

  constructor() { }

  ngOnInit() {
    this.strategies = this.res;
    if(this.strategies.response == 1 ){
      this.strategies.data.forEach((strategy,i)=>{
        this.labels.push(strategy.date);
        strategy.list.forEach((classe,j)=>{
          var idClasse=classe.id;
          if(this.dati[idClasse-1]==undefined){
            this.dati[idClasse-1]=new Array(this.strategies.data.length);
          }
          this.dati[idClasse-1][i]=classe.percentage;
        })
      })
    }
    //INSERIMENTO ZERI
    for(var i=0;i<this.dati.length;i++){
      for(var j=0;j<this.dati[i].length;j++){
        if(this.dati[i][j]==undefined){
          this.dati[i][j]={percentage:0,name:''};
        }
      }
    }
    this.dataclass=["bonds","forex","stocks","commodities"];

    for(var i=0;i<this.dati.length;i++){
      console.log("this.dati[i]: ",this.dati[i])
      this.chartData.push({data: this.dati[i], label:this.dataclass[i]});
    }
    console.log("chartData: ",this.chartData);
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
