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
    @Input() percentage;
    @Input() color;
    @Input() reset;
    oldValue: number;
    //strategies:Map<number, AssetClassStrategy> = new Map<number, AssetClassStrategy>();
    constructor(public StrategyService: StrategyService) { }

    ngOnInit() {

    }

    handleSlide(e) : void {
        this.oldValue = this.StrategyService.strategies.get(this.id).percentage;
        this.StrategyService.strategies.get(this.id).getAssetClass().setName(this.value);
        this.StrategyService.strategies.get(this.id).setPercentage(this.percentage);
        this.percentage = this.StrategyService.createAssetClassStrategy(this.id, this.oldValue);
    }
    // handleChange(e) {
    // }
    // assignFinancialData(){
    //
    // }

}
