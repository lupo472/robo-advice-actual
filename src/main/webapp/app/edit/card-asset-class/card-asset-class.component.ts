import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { StrategyService } from '../../services/strategy.service';
import { AssetClassStrategy } from '../../model/asset-class-strategy';
import { AssetClass } from '../../model/asset-class';

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
    assetClassStrategy:AssetClassStrategy;
    percentage:number;
    oldValue:number;
  constructor(public StrategyService:StrategyService) {
    this.percentage = 0;


  //  this.strategies.get(this.id);
    //this.assetClassStrategy = new AssetClassStrategy(0,new AssetClass(0,""));
    //this.assetClassStrategy = this.StrategyService.assetClassStrategy;
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
    this.oldValue = this.StrategyService.strategies.get(this.id).percentage;
    this.StrategyService.strategies.get(this.id).getAssetClass().setName(this.value);
    this.StrategyService.strategies.get(this.id).setPercentage(this.percentage);
    // console.log(this.StrategyService.strategies.get(this.id).getAssetClass());
    // console.log(this.StrategyService.strategies.get(this.id).getPercentage());
    this.percentage = this.StrategyService.createAssetClassStrategy(this.id,this.oldValue);  
  }
  // handleChange(e) {
  // }


}
