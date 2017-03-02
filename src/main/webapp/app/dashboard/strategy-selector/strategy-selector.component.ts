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
  
  //ASSIGN COLOUR
  assignColour(id){
  
    switch(id){
        case 1: return "#4dbd74";
        case 2: return "#63c2de";
        case 3: return "#f8cb00";
        case 4: return "#f86c6b";
        }
  }
  
  //ASSIGN STRATEGIES
  getStrategy(result){
    this.strategySet = result.data;
    
    var arrayPercentage = [];
    var arrayNull = [];
    var arrayLabels = [];
    var arrayColours = [];
    
    this.strategySet.forEach((item, index) => {
      
      item.list.forEach((element, i) =>{
       arrayPercentage[i] = element.percentage;
       arrayNull[i] = 0;
       arrayLabels[i] = element.name;
       arrayColours[i] = this.assignColour(element.id);
        });
      
      this.strategies[index] = {
                                name:  item.name, 
                                data: arrayPercentage, 
                                labels: arrayLabels, 
                                colours: [{backgroundColor: arrayColours, borderWidth: 3}]
                                }
      
      console.log(this.strategies[index].colours);
      arrayPercentage = [];
      arrayLabels = [];
      arrayColours = [];
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
  
  ngOnInit() {
  }

}
