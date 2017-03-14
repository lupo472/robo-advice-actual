import { AppConfig } from '../../services/app.config';
import { Component, OnInit, Input, OnChanges, SimpleChanges} from '@angular/core';
import { StrategyService } from '../../services/strategy.service';
import { AssetService } from '../../services/asset.service';

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
    arrayPercentage: Array<number> = [];
    arrayColor: Array<string> = [];
    arrayColors: Array<any> = [];
    arrayLabels: Array<string> = [];
    constructor(private AssetService: AssetService) {

    }
    ngOnInit() {
        this.createChart();
    }
    ngOnChanges(changes: SimpleChanges) {
        // console.log("something changes");
        // console.log(changes);

    }
    createChart(): void {
        for (let assetClass of this.strategy.list) {
            this.arrayPercentage.push(assetClass.percentage);
            this.arrayLabels.push(assetClass.name);
            this.arrayColor.push(this.assignColour(assetClass.id));
            this.arrayColors = [{ backgroundColor: this.arrayColor, borderWidth: 3 }];
        }
    }
    assignColour(id): string {
        return this.AssetService.assignColour(id);
    }
    //  console.log("strategy inside");
    //  console.log(this.arrayPercentage);
    //  console.log(this.arrayColors);



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
