import { Component, OnInit,Input,trigger,ViewChild,NgZone,
  state,
  style,
  animate,
  transition} from '@angular/core';
import {StrategyService} from "../../services/strategy.service";
import {Strategy} from "../../model/strategy";
import {DefaultStrategy} from "../../model/default-strategy";

@Component({
  selector: 'app-tab-menu',
  templateUrl: './tab-menu.component.html',
  styleUrls: ['./tab-menu.component.scss'],
  animations: [
    trigger('flyInOut', [
      state('active', style({transform: 'translateX(0)'})),
      transition('void => *', [
        style({transform: 'translateX(-100%)'}),
        animate(100)
      ]),
      transition('* => void', [
        animate(100, style({transform: 'translateX(100%)'}))
      ])
    ])
  ]
})
export class TabMenuComponent implements OnInit {
  activeStrategy:Strategy;
  adviceStrategy:Strategy;
  renderActive = false;
  renderAdvice = false;
  render = false;
  name:string;
  adviceState = "inactive";
  @ViewChild('chartGraph') public mystrategy;
  @ViewChild('advice') public advice;
  constructor(private _z: NgZone,private StrategyService:StrategyService) { }

  ngOnInit() {
    this.StrategyService.getActiveStrategy().subscribe(activeStrategy => this.getActiveStrategy(activeStrategy));
    this.StrategyService.getAdvice(7).subscribe(adviceStrategy => this.getAdviceStrategy(adviceStrategy));
  }
  getActiveStrategy(activeStrategy){
    this.activeStrategy = new Strategy();
    this.activeStrategy = activeStrategy;
    if (activeStrategy instanceof Strategy) {
      this.renderActive = true;
    }
  }
  getAdviceStrategy(adviceStrategy){
    this.adviceStrategy = new DefaultStrategy();
    this.adviceStrategy = adviceStrategy;
    if (adviceStrategy instanceof DefaultStrategy) {
      this.renderAdvice = true;
    }
    this.adviceState = "active";
  }
  receiveStrategy(strategy) {
    console.log("",strategy);
    this.StrategyService.setDefaultStrategySended(strategy);
    /*this._z.run(() => {
      this.activeStrategy.setStrategyArray(strategy.getStrategyArray());
      this.activeStrategy.resetArray();
      this.activeStrategy.createChart();
      console.log("ffffffffff",this.activeStrategy);
    });*/
  }



}
