import { AppConfig } from '../../services/app.config';
import { Component, OnInit, Input } from '@angular/core';
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
  
  constructor(private StrategyService:StrategyService) { }

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
