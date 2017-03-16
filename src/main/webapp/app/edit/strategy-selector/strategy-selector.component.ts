import { AppConfig } from '../../services/app.config';
import { StrategyService } from '../../services/strategy.service';
import { Component, OnInit, Input, OnChanges, SimpleChanges} from '@angular/core';
import { DefaultStrategies } from '../../model/default-strategies';
import { Strategies } from '../../model/strategies';

@Component({
    selector: 'app-strategy-selector',
    templateUrl: './strategy-selector.component.html',
    styleUrls: ['./strategy-selector.component.scss']
})
export class StrategySelectorComponent implements OnInit, OnChanges {

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
    //strategies:Strategies;

    constructor() {

    }
    ngOnInit() {
      //this.strategies = new Strategies();
      //this.strategies.createStrategyForChart(this.strategy);
      this.strategy.createChart();
    }
    ngOnChanges(changes: SimpleChanges) {
        // console.log("something changes");
        // console.log(changes);
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
