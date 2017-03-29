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
    public options={};

    constructor() {}

    resetStrategies():void{
        this.strategies=[];
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
    }

    getCurrentStrategy(): Strategy {
        return this.currentStrategy;
    }

    /************ CHART HISTORY VERSION 1*************************************/
    /*createChartDataHistory(data: any, startdate: Date) {
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
    }*/
    /***************************************************************/



    createHistoryChartOptions(data: any, startdate: Date) {
        this.chartData = [];
        this.labels = [];
        let k:number=0;
        let StringStrategy:string="";
        let JSONStrategy;

        data.forEach(strategy => {
            StringStrategy="";
            let beginning = new Date(strategy.date);

            if (beginning >= startdate) {

                StringStrategy='{"category": "'+strategy.date+'",';
                strategy.list.forEach((assetClass,i) => {
                    StringStrategy=StringStrategy+'"'+assetClass.name+'":'+assetClass.percentage;
                    if(i!= strategy.list.length-1){
                        StringStrategy=StringStrategy+',';
                    }
                });
                StringStrategy=StringStrategy+'}';
                JSONStrategy=JSON.parse(StringStrategy);
                this.chartData[k]=JSONStrategy;
                k++;
            }

        });
        console.log("chartData:#############",this.chartData);

        this.options={
            "type": "serial",
            "legend": {
                "horizontalGap": 10,
                "useGraphSettings": true,
                "markerSize": 10
            },
            "dataProvider": this.chartData,
            "valueAxes": [ {
                "stackType": "100%",
                "axisAlpha": 0,
                "gridAlpha": 0
            } ],
            "graphs": [ {
                "balloonText": "<b>[[title]]</b><br><span style='font-size:14px'>[[category]]: <b>[[value]]</b></span>",
                "fillAlphas": 0.8,
                "labelText": "[[percents]]%",
                "lineAlpha": 0.3,
                "title": "Europe",
                "type": "column",
                "color": "#000000",
                "valueField": "bonds",
                "fillColors": "#4dbd74"
            }, {
                "balloonText": "<b>[[title]]</b><br><span style='font-size:14px'>[[category]]: <b>[[value]]</b></span>",
                "fillAlphas": 0.8,
                "labelText": "[[percents]]%",
                "lineAlpha": 0.3,
                "title": "North America",
                "type": "column",
                "color": "#000000",
                "valueField": "forex",
                "fillColors": "#63c2de"
            }, {
                "balloonText": "<b>[[title]]</b><br><span style='font-size:14px'>[[category]]: <b>[[value]]</b></span>",
                "fillAlphas": 0.8,
                "labelText": "[[percents]]%",
                "lineAlpha": 0.3,
                "title": "Asia-Pacific",
                "type": "column",
                "newStack": true,
                "color": "#000000",
                "valueField": "stocks",
                "fillColors": "#f8cb00"
            }, {
                "balloonText": "<b>[[title]]</b><br><span style='font-size:14px'>[[category]]: <b>[[value]]</b></span>",
                "fillAlphas": 0.8,
                "labelText": "[[percents]]%",
                "lineAlpha": 0.3,
                "title": "Latin America",
                "type": "column",
                "color": "#000000",
                "valueField": "commodities",
                "fillColors": "#f86c6b"
            }],
            "categoryField": "category",
            "categoryAxis": {
                "gridPosition": "start",
                "axisAlpha": 0,
                "gridAlpha": 0,
                "position": "left"
            },
            "export": {
                "enabled": true
            }

        }


        return this.options;
    }
}
