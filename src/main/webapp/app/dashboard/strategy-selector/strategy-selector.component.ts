import { AppConfig } from '../../services/app.config';
import { Component, OnInit, Input } from '@angular/core';

import { StrategyService } from '../../services/strategy.service';

@Component({
  selector: 'app-strategy-selector',
  templateUrl: './strategy-selector.component.html',
  styleUrls: ['./strategy-selector.component.scss'],
  providers:[StrategyService]
})
export class StrategySelectorComponent implements OnInit {
  
  public strategySet = [];
  public strategies =  [];

  constructor(private service:StrategyService) {
    this.service.getDefaultStrategySet().subscribe((result) => this.getStrategy(result));
   }
  
  //ASSIGN STRATEGIES
  getStrategy(result){
    this.strategySet = result.data;
    
    var arrayPercentage = [];
    var arrayNull = [];
    var arrayLabels = [];
    
    this.strategySet.forEach((item, index) => {
      
      item.list.forEach((element, i) =>{
       arrayPercentage[i] = element.percentage;
       arrayNull[i] = 0;
       arrayLabels[i] = element.name;
        });
      
      this.strategies[index] = {name:  item.name, data: arrayPercentage, labels: arrayLabels}
      arrayPercentage = [];
      arrayLabels = [];
    })
  
    this.strategies[this.strategySet.length]={name: 'custom', data: arrayNull}
  }
  
  //GENERAL SETTINGS
  
  public strategyOptions:any = {
    maintainAspectRatio: false,
    cutoutPercentage: 20,
    legend: {
      display: false
    }
  };
  public strategyType:string = 'pie';
  public strategyColours:Array<any> = [
    {
      backgroundColor: [
                "#4dbd74",
                "#63c2de",
                "#f8cb00",
                "#f86c6b"
            ],
      borderWidth: 5
    }
  ];
  
  ngOnInit() {
  }

}
