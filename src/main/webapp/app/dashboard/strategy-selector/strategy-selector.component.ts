import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-strategy-selector',
  templateUrl: './strategy-selector.component.html',
  styleUrls: ['./strategy-selector.component.scss']
})
export class StrategySelectorComponent implements OnInit {

  constructor() { }
  
  //STRATEGIES
  public strategies:Array<any> =  [
                                   {name: 'Bonds',    data: [95, 0, 0, 5]   },
                                   {name: 'Income',   data: [65, 15, 10, 10]},
                                   {name: 'Balanced', data: [30, 20, 30, 20]},
                                   {name: 'Growth',   data: [20, 10, 60, 10]},
                                   {name: 'Stocks',   data: [0, 0, 100, 0]},
                                   {name: 'Custom',   data: [0, 0, 0, 0]    }
                                 ];
  
  
  //GENERAL SETTINGS
  public strategyLabels:Array<any> = ['Bonds', 'Forex', 'Stocks', 'Commodities'];
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
