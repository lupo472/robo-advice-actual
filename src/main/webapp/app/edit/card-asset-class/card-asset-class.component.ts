import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { StrategyService } from '../../services/strategy.service';
import { AssetClassStrategy } from '../../model/asset-class-strategy';
import { AssetClass } from '../../model/asset-class';

@Component({
    selector: 'app-card-asset-class',
    templateUrl: './card-asset-class.component.html',
    styleUrls: ['./card-asset-class.component.scss']
})

export class CardAssetClassComponent implements OnInit{
    @Input() value;
    @Input() isCustom;
    @Input() financialData;
    @Input() id;
    @Input() percentage;
    @Input() color;
    @Input() reset;
    @Output() updatePercentage = new EventEmitter();
    @Output() update = new EventEmitter();
    oldValue: number;

    constructor(public StrategyService: StrategyService) { }

    ngOnInit() {

    }
    show(){
        this.update.emit();
    }
    handleSlide(e) : void {
        let currentAssetClassStrategy = this.StrategyService.customStrategy.getAssetClassStrategyMap().get(this.id);
        this.oldValue = currentAssetClassStrategy.getPercentage();
        currentAssetClassStrategy.setName(this.value);
        currentAssetClassStrategy.setPercentage(this.percentage);
        this.percentage = this.StrategyService.customStrategy.setPercentageWithSlider(this.id,this.oldValue);
        this.updatePercentage.emit();
    }
    // handleChange(e) {
    // }
}
