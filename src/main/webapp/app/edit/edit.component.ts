import {Component, OnInit, Renderer, ViewChild, AfterViewInit, NgZone} from '@angular/core';
import { Router } from '@angular/router';
import { AssetService } from '../services/asset.service';
import { StrategyService } from '../services/strategy.service';
import { Cookie } from 'ng2-cookies';
import { ModalDirective } from 'ng2-bootstrap/modal/modal.component';
import { DefaultStrategy } from '../model/default-strategy';
import { CustomStrategy } from '../model/custom-strategy';
import { Strategy } from '../model/strategy';
import { AssetClassStrategy } from '../model/asset-class-strategy';
import {FinancialData} from "../model/financial-data";
import {FinancialDataSet} from "../model/financial-data-set";
import {BaseChartDirective} from "ng2-charts";

@Component({
    templateUrl: 'edit.component.html'
})

export class EditComponent implements OnInit, AfterViewInit {
    public isCustom: boolean;
    public strategies: Strategy[] = [];
    public financialDataSet: FinancialData[] = [];
    public financialDataSetModal: FinancialData[] = [];
    public financialDataModal: any = {};
    public assetClassStrategies: AssetClassStrategy[] = [];
    public selected = [];
    public isDisabled = true;
    public renderModalGraph:boolean = false;
    public chartModalId;
    reset = false;
    @ViewChild('childModal') public childModal: ModalDirective;
    @ViewChild('chartModal') public chartModal: ModalDirective;
    @ViewChild('chart') public chart: BaseChartDirective;

    constructor(private _z: NgZone, public AssetService: AssetService, public StrategyService: StrategyService, private router: Router) {
        this.isCustom = false;
    }

    public showChildModal(): void {
        this.childModal.show();
    }

    public hideChildModal(): void {
        this.childModal.hide();
    }
    public hideChartModal(){
        this.chartModal.hide();
        this.renderModalGraph = false;
    }
    public handleShow(id){
        console.log(id);
        this.financialDataModal = this.financialDataSetModal[id-1];
        this.renderModalGraph = true;
        console.log("financial",this.financialDataModal);
        this.chartModal.show();

    }
    ngAfterViewInit() {
        // viewChild is set after the view has been initialized
        this.childModal.show();
    }

    ngOnInit(): void {
        this.AssetService.getFinancialDataSet(365,"small").subscribe((res => this.getFinancialData(res)));
        this.AssetService.getFinancialDataSet(1000,"big").subscribe((res => this.getFinancialDataModal(res)));
        this.StrategyService.getActiveStrategy().subscribe();
    }
    getFinancialDataModal(res){
        this.financialDataSetModal = res;
        //this.renderModalGraph = true;
        console.log("FinancialDataSetModal", this.financialDataSetModal);
        this.AssetService.getAssetClassSet().subscribe((res) => this.getAssetClass(res));
    }
    getFinancialData(res) {
        this.financialDataSet = res;
        console.log("FinancialDataSet", this.financialDataSet);
        this.AssetService.getAssetClassSet().subscribe((res) => this.getAssetClass(res));
    }

    //ASSIGN STRATEGIES
    getStrategy(res): void {
        this.strategies = res.getStrategies();
    }

    //ASSIGN ASSET CLASS
    getAssetClass(res): void {
        this.StrategyService.getDefaultStrategySet().subscribe(res => this.getStrategy(res));
        this.assetClassStrategies = res.getAssetClassStrategies();
    }

    createStrategy(): void {
        this.StrategyService.createStrategy(this.StrategyService.strategies.getCurrentStrategy()).subscribe(
            (res) => {
                this.router.navigate(['dashboard']);
            });
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
        console.log("strat", strategy);
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



