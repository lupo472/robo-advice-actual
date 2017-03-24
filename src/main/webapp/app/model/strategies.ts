import {AssetClassStrategy} from './asset-class-strategy';
import {Strategy} from './strategy';
import {DefaultStrategy} from './default-strategy';

export class Strategies {
    private strategies: Strategy[] = [];
    private currentStrategy: Strategy = new Strategy();

    public chartData = [];
    public dataclass = ["bonds", "forex", "stocks", "commodities"]; // da sostituire con il model
    public colorclass = ["#4dbd74", "#63c2de", "#f8cb00", "#f86c6b"]; // da sostituire con il model
    public labels = [];
    public dati = [];
    public render: boolean = false;

    constructor() {
    }

    setStrategies(strategies: Strategy[]): void {
        this.strategies = strategies;
    }

    addStrategy(strategy: Strategy): void {
        this.strategies.push(strategy);
    }

    getStrategies(): Strategy[] {
        return this.strategies;
    }

    createStrategies(data): void {
        data.forEach((item, i) => {
            let defaultStrategy = new DefaultStrategy();
            defaultStrategy.setName(item.name);
            item.list.forEach((element, i) => {
                defaultStrategy.addAssetClassStrategy(new AssetClassStrategy(element.percentage,
                    element.id, element.name));
            });
            this.addStrategy(defaultStrategy);
        });
    }

    setCurrentStrategy(strategy: Strategy) {
        this.currentStrategy = strategy;
        console.log("DEFAULTSTRATEGY", this.currentStrategy);
    }

    getCurrentStrategy(): Strategy {
        return this.currentStrategy;
    }

    createTrendLabelHistory():any{

    }

    createChartDataHistory(data: any, startdate: Date) {
        this.chartData = [];
        this.labels = [];

        data.forEach((strategy, i) => {
            let beginning = new Date(strategy.date);

            if (beginning >= startdate) {

                this.labels.push(strategy.date);
                strategy.list.forEach(assetClass => {
                    let id = assetClass.id;

                    if (this.dati[id - 1] == undefined) {
                        this.dati[id - 1] = new Array(data.length);
                    }
                    this.dati[id - 1][i] = assetClass.percentage;
                })
            }
        });

        for(var k=0;k<this.dataclass.length;k++){
            if(this.dati[k]==undefined){
                this.dati[k]=new Array(this.labels.length);
            }
        }

        //INSERIMENTO ZERI
        for (let i = 0; i < this.dati.length; i++) {
            for (let j = 0; j < this.dati[i].length; j++) {
                if (this.dati[i][j] == undefined) {
                    this.dati[i][j] = 0;
                }
            }

            this.chartData.push({data: this.dati[i], label: this.dataclass[i], backgroundColor: this.colorclass[i]});
        }

        return {data: this.chartData, labels: this.labels};
    }
}
