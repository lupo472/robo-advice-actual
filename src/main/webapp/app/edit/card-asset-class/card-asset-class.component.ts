import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { StrategyService } from '../../services/strategy.service';
import { Strategy } from '../../model/strategy';

@Component({
  selector: 'app-card-asset-class',
  templateUrl: './card-asset-class.component.html',
  styleUrls: ['./card-asset-class.component.scss']
})

export class CardAssetClassComponent implements OnInit {
    @Input() value;
    @Input() lineChartData;
    @Input() lineChartLabels;
    @Input() lineChartOptions;
    @Input() lineChartColours;
    @Input() lineChartLegend;
    @Input() lineChartType;
    @Input() isCustom;
    @Input() id;
    strategy:Strategy;

  constructor(public StrategyService:StrategyService) {
    //this.strategy.percentage = 0;
  }

  public brandPrimary:string =  '#20a8d8';
  public brandSuccess:string =  '#4dbd74';
  public brandInfo:string =   '#63c2de';
  public brandWarning:string =  '#f8cb00';
  public brandDanger:string =   '#f86c6b';

  ngOnInit() {
  }

  handleSlide(e) {
    this.strategy.assetClass.id = this.id;
    this.strategy.assetClass.name = this.value;
    this.StrategyService.onStrategy(this.strategy);
    console.log(this.StrategyService.strategies);
  }
  // handleChange(e) {
  // }


}
