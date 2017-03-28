import { Component, OnInit,Input,Output,EventEmitter,NgZone} from '@angular/core';
import {Strategy} from "../../model/strategy";
import {DefaultStrategy} from "../../model/default-strategy";
import {StrategyService} from "../../services/strategy.service";

@Component({
  selector: 'app-strategy-graph',
  templateUrl: './strategy-graph.component.html',
  styleUrls: ['./strategy-graph.component.scss'],

})
export class StrategyGraphComponent implements OnInit {
  @Input() strategy: Strategy;
  @Output() sendStrategy = new EventEmitter();
  isAdvice = false;
  name:string;
  constructor(private _z: NgZone,private StrategyService:StrategyService) { }

  ngOnInit() {
      this.strategy.createChart();

    if (this.strategy instanceof DefaultStrategy) {
      this.name = "Got Advice!";
    } else {
      this.name = "MyStrategy";
      this.isAdvice = true;
    }
  }
  changeToStrategy(){
    this.StrategyService.changeToAdviceStrategy(this.strategy).subscribe(res => console.log(res));
    this.sendStrategy.emit(this.strategy);
  }
  //GENERAL SETTINGS
  public strategyOptions: any = {
    maintainAspectRatio: false,
    cutoutPercentage: 20,
    legend: {
      display: false
    }
  };
  //GRAPH TYPE
  public strategyType: string = 'pie';

}
