import { Component, OnInit } from '@angular/core';
import {StrategyService} from "../../services/strategy.service";
import {Strategy} from "../../model/strategy";

@Component({
  selector: 'app-tab-menu',
  templateUrl: './tab-menu.component.html',
  styleUrls: ['./tab-menu.component.scss']
})
export class TabMenuComponent implements OnInit {
  activeStrategy:Strategy;
  adviceStrategy:Strategy;
  render = false;
  constructor(private StrategyService:StrategyService) { }

  ngOnInit() {
    this.StrategyService.getActiveStrategy().subscribe(activeStrategy => this.getActiveStrategy(activeStrategy));
    //this.StrategyService.getAdvice(15).subscribe(adviceStrategy => this.getAdviceStrategy(adviceStrategy));
  }
  getActiveStrategy(activeStrategy){
    this.activeStrategy = activeStrategy;
    this.render = true;
    console.log("act",this.activeStrategy);
  }
  getAdviceStrategy(adviceStrategy){
    this.adviceStrategy = adviceStrategy;
    this.render = true;
    console.log("adv",this.adviceStrategy);
  }



}
