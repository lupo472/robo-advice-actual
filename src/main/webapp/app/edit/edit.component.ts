import {Component, OnInit, Renderer, ViewChild, AfterViewInit, NgZone, OnDestroy} from '@angular/core';
import { Router } from '@angular/router';
import { AssetService } from '../services/asset.service';
import { StrategyService } from '../services/strategy.service';
import { ModalDirective } from 'ng2-bootstrap/modal/modal.component';
import { CustomStrategy } from '../model/custom-strategy';
import { Strategy } from '../model/strategy';
import { AssetClassStrategy } from '../model/asset-class-strategy';
import {FinancialData} from "../model/financial-data";
import { DetailMarketValuesChart } from '../model/detail-market-values-chart';


@Component({
    templateUrl: 'edit.component.html'
})

export class EditComponent implements OnInit, AfterViewInit, OnDestroy {
    public detailOptions:DetailMarketValuesChart = new DetailMarketValuesChart();
    public isCustom: boolean;
    public strategies: Strategy[] = [];
    public financialDataSet: FinancialData[] = [];
    public financialDataSetAmChart: FinancialData[] = [];
    public assetClassStrategies: AssetClassStrategy[] = [];
    public selected = [];
    errorMessage:string;
    isDisabled = true;
    render = false;
    @ViewChild('childModal') public childModal: ModalDirective;
    @ViewChild('chartTest') public chartTest;

    constructor(private _z: NgZone, public AssetService: AssetService, public StrategyService: StrategyService, private router: Router) {
        this.isCustom = false;
    }
    public hideChildModal(): void {this.childModal.hide();}
    public handleShow(id){
        this.render= true;
        let dataProvider = this.financialDataSetAmChart[id-1].getFinancialData();
        let color = this.financialDataSetAmChart[id-1].assignColour();
        this.chartTest.changeChart(dataProvider,color);
    }
    ngOnInit(): void {
        this.AssetService.getFinancialDataSet(365,"small").subscribe(
            (res) => this.getFinancialData(res),
            (error) => console.log(this.errorMessage = <any>error));
        this.StrategyService.getActiveStrategy().subscribe();
    }
    //ASSIGN FINANCIAL DATA
    getFinancialData(res) {
        this.financialDataSet = res;
        this.AssetService.getAssetClassSet().subscribe(
            (res) => this.getAssetClass(res),
            (error) => console.log(this.errorMessage = <any>error));
    }
    //ASSIGN ASSET CLASS
    getAssetClass(res): void {
        this.StrategyService.getDefaultStrategySet().subscribe(res => this.getStrategy(res));
        this.assetClassStrategies = res.getAssetClassStrategies();
    }
    //ASSIGN STRATEGIES
    getStrategy(res): void {
        this.strategies = res.getStrategies();
        this.AssetService.getFinancialDataSet(1000,"big").subscribe((res => this.getFinancialDataModal(res)));
    }
    //ASSIGN FINANCIAL DATA FOR MORE YEARS
    getFinancialDataModal(res){
        this.financialDataSetAmChart = res;
    }
    ngAfterViewInit() {
        // viewChild is set after the view has been initialized
        this.childModal.show();
    }
    ngOnDestroy(){
        console.log("onDestroy");
    }
    createStrategy(): void {
        this.StrategyService.createStrategy().subscribe(
            (res) => {this.router.navigate(['dashboard'])},
            (error) => console.log(this.errorMessage = <any>error));
    }
    resetSlider() {
        this.isCustom = false;
        let currentStrategy = this.StrategyService.strategies.getCurrentStrategy();
        if (currentStrategy instanceof CustomStrategy) {
            currentStrategy.resetSlider(this.StrategyService.activeStrategy);
        }
    }
    handleUpdatePercentage() {
        this._z.run(() => {
            this.StrategyService.customStrategy.rePaint();
        });
    }
    onSelect(strategy: Strategy, i): void {
        this.StrategyService.strategies.setCurrentStrategy(strategy);
        if (strategy instanceof CustomStrategy) {
            this.isCustom = true;
        } else {
            this.isCustom = false;
        }
        this.assetClassStrategies = strategy.getStrategyArray();
        this.isDisabled = false;
        this.strategies.forEach((item, index) => {
            if (index == i) {
                this.selected[index] = true;
            } else {
                this.selected[index] = false;
            }
        });
    }
}



