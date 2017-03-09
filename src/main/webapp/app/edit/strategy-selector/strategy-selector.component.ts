import { AppConfig } from '../../services/app.config';
import { Component, OnInit, Input} from '@angular/core';
import { StrategyService } from '../../services/strategy.service';

@Component({
  selector: 'app-strategy-selector',
  templateUrl: './strategy-selector.component.html',
  styleUrls: ['./strategy-selector.component.scss']
})
export class StrategySelectorComponent implements OnInit {

  @Input() data;
  @Input() labels;
  @Input() options;
  @Input() colors;
  @Input() legend;
  @Input() chartType;
  @Input() name;
  @Input() id;
  @Input() selected;
  @Input() strategy;
  arrayPercentage:any;
  arrayColor:any;
  arrayColors:any;
  constructor(private StrategyService:StrategyService) {
    this.arrayPercentage =  [];
    this.arrayColor = [];
    this.arrayColors = {};
   }

   ngOnInit() {
      for (let asset of this.strategy.list) {
        this.arrayPercentage.push(asset.percentage);
        this.arrayColor.push(asset.color);
        this.arrayColors = [{backgroundColor:this.arrayColor,borderWidth:3}];
    }
    //  console.log("strategy inside");
    //  console.log(this.arrayPercentage);
    //  console.log(this.arrayColors);

   }

  ngOnInit() { }

  public chartHovered(e: any): void {
    console.log(e);
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

}
