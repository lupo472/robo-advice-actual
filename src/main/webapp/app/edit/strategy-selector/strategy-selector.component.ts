import { Component, OnInit, Input } from '@angular/core';
import {Strategy} from "../../model/strategy";
import {CustomStrategy} from "../../model/custom-strategy";

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
    @Input() strategy: Strategy;
    custom = false;
    clicked= true;

    constructor() {}
    ngOnInit() {
      this.strategy.createChart();
      if (this.strategy instanceof CustomStrategy) {
          if (this.strategy.getName() == "Select your strategy") {
              this.custom = true;
              console.log("sono una cazzo di custom");
          }
      }
    }
    public chartHovered(e: any): void {
        console.log(e);
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
