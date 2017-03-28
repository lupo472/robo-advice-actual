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
    public forecastDataSetAmChart: FinancialData[] = [];
    public assetClassStrategies: AssetClassStrategy[] = [];
    public selected = [];
    errorMessage:string;
    isDisabled = true;
    ren = false;
    render:string="nullo";
    market=true;
    forecasting=true;
    renderMarket=false;
    renderForecasting=false;
    @ViewChild('childModal') public childModal: ModalDirective;
    @ViewChild('chartGraph') public chartGraph;

    constructor(private _z: NgZone, public AssetService: AssetService, public StrategyService: StrategyService, private router: Router) {
        this.isCustom = false;
    }
    public hideChildModal(): void {this.childModal.hide();}

    handleUpdate(render,id){
       if (render == "market"){
           this.renderMarket = true;
           this.renderForecasting = false;
           this.market=false;
           let dataProvider = this.financialDataSetAmChart[id-1].getFinancialData();
           let color = this.financialDataSetAmChart[id-1].assignColour();
           this.chartGraph.changeChart(dataProvider,color);
       }
       if (render == "forecasting"){
           this.renderMarket = false;
           this.renderForecasting = true;
           this.market=false;
           let dataProviderForecast = this.forecastDataSetAmChart[id-1].getFinancialData();
           let colorForecast = this.forecastDataSetAmChart[id-1].assignColour();
           this.chartGraph.changeChart(dataProviderForecast,colorForecast);
       }
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
            (resp) => this.getAssetClass(resp),
            (error) => console.log(this.errorMessage = <any>error));
    }
    //ASSIGN ASSET CLASS
    getAssetClass(res): void {
        this.StrategyService.getActiveStrategy().subscribe();
        this.StrategyService.getDefaultStrategySet().subscribe(res => this.getStrategy(res));
        this.assetClassStrategies = res.getAssetClassStrategies();
    }
    //ASSIGN STRATEGIES
    getStrategy(res): void {
        this.strategies = res.getStrategies();
        this.AssetService.getFinancialDataSet(1000,"big").subscribe((res => this.getFinancialDataModal(res)));
        this.AssetService.getForecast(90,"big").subscribe(res=>this.assignForecast(res));
    }
    //ASSIGN FINANCIAL DATA FOR MORE YEARS
    getFinancialDataModal(res){
        this.financialDataSetAmChart = res;
    }
    assignForecast(res){
        this.forecastDataSetAmChart = res;
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



