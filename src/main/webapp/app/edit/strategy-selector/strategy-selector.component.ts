import { AppConfig } from '../../services/app.config';
import { StrategyService } from '../../services/strategy.service';
import { Component, OnInit, Input } from '@angular/core';
import { DefaultStrategies } from '../../model/default-strategies';
import { Strategies } from '../../model/strategies';
import {Strategy} from "../../model/strategy";

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

    constructor() {

    }
    ngOnInit() {
      this.strategy.createChart();
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
    public strategyType: string = 'pie';

}
