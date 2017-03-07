import { AppConfig } from '../../services/app.config';
import { Component, OnInit, Input } from '@angular/core';
import { StrategyService } from '../../services/strategy.service';

@Component({
  selector: 'app-strategy-selector',
  templateUrl: './strategy-selector.component.html',
  styleUrls: ['./strategy-selector.component.scss'],
  //providers:[StrategyService]
})
export class StrategySelectorComponent implements OnInit {
  public strategySet = [];
  public strategies =  [];

  constructor(private StrategyService:StrategyService) {
      this.StrategyService.getDefaultStrategySet().subscribe(res=> this.getStrategy(res));
   }

  //ASSIGN STRATEGIES
  getStrategy(res){
    this.strategies = res;
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
