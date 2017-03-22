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
    /*@Input() lineChartData;
    @Input() lineChartLabels;
    @Input() lineChartOptions;
    @Input() lineChartColours;
    @Input() lineChartLegend;
    @Input() lineChartType;*/
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
    // assignFinancialData(){
    //
    // }

//LINECHART GENERAL
    /*public lineChartLabels = ['January', 'February', 'March', 'April', 'May', 'June', 'July'];
    public lineChartOptions: any = {
        maintainAspectRatio: false,
        tooltips: {
            callbacks: {
                label: function(tooltipItem) {
                    return tooltipItem.yLabel;
                }
            }
        },
        scales: {
            xAxes: [{
                gridLines: {
                    color: 'transparent',
                    zeroLineColor: 'transparent'
                },
                ticks: {
                    fontSize: 2,
                    fontColor: 'transparent',
                }

            }],
            yAxes: [{
                display: false,
                ticks: {
                    display: false,
                    min: 40 - 5,
                    max: 84 + 5,
                }
            }],
        },
        elements: {
            line: {
                borderWidth: 1
            },
            point: {
                radius: 4,
                hitRadius: 10,
                hoverRadius: 4,
            },
        },
        legend: {
            display: false
        }
    };
    public lineChartType: string = 'line';
    public lineChartColours: Array<any> = [
        {
            backgroundColor: 'white',
            borderColor: 'rgba(255,255,255,.55)'
        }
    ];*/
}
