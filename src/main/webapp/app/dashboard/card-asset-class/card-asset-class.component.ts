import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { AssetClass } from '../assetclass';

@Component({
  selector: 'app-card-asset-class',
  templateUrl: './card-asset-class.component.html',
  styleUrls: ['./card-asset-class.component.scss']
})

export class CardAssetClassComponent implements OnInit {
    strategy:any;
    @Input() value;
    @Input() percent;
    @Input() lineChartData;
    @Input() lineChartLabels;
    @Input() lineChartOptions;
    @Input() lineChartColours;
    @Input() lineChartLegend;
    @Input() lineChartType;
    @Input() isCustom;
    @Input() id;
    @Output() onStrategy;

  constructor() {
    this.strategy = {};
    this.onStrategy = new EventEmitter<any>();
  }

  public brandPrimary:string =  '#20a8d8';
  public brandSuccess:string =  '#4dbd74';
  public brandInfo:string =   '#63c2de';
  public brandWarning:string =  '#f8cb00';
  public brandDanger:string =   '#f86c6b';

  ngOnInit() {

  }

  public showPercentage(){
    this.strategy.id_asset = this.id;
    this.onStrategy.emit(this.strategy);
  }

}
