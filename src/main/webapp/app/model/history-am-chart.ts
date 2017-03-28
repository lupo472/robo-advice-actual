import {Strategies} from "./strategies";
import {Strategy} from "./strategy";
import {AssetClassStrategy} from "./asset-class-strategy";
export class HistoryAmChart {
    public options={};
    public chartData = [];
    public graphs= [];
    public trendLabels;
    public strategies= new Strategies();
    constructor() {}

    createTrendLabelHistory(labels,portfolio){

        console.log("PORTFOLIO",portfolio);
        console.log("labels",labels);

        labels.forEach((label,i)=>{
            portfolio.labels.forEach((labelPortfolio,j)=>{
                if(label===labelPortfolio && portfolio.datasets[0].data[j]){
                    this.trendLabels[i]={};
                    this.trendLabels[i].startvalue=(portfolio.datasets[0].data[j]);
                    if(i!==0){
                        this.trendLabels[i-1].endvalue=this.trendLabels[i].startvalue;
                    }
                }
            });
            if(i+1===labels.length && this.trendLabels[i]){ //case of the last strategy that is analyzed
                console.log("strategie terminate:",i+1);
                this.trendLabels[i].endvalue=(portfolio.datasets[0].data[portfolio.datasets[0].data.length-1]);
            }
        });

    }
    createHistoryChartOptions(data: any, startdate: Date, portfolio: any) {
        this.chartData = [];
        this.strategies.resetStrategies();
        this.graphs=[];
        this.trendLabels=[];
        let k:number=0;
        let StringStrategy:string="";
        let JSONStrategy;
        let labels=[];

        if(portfolio){
            console.log("dentro if");
            data.forEach(strategy => {
                let beginning = new Date(strategy.date);
                if (beginning >= startdate) {
                    labels.push(strategy.date);
                }
            });
            this.createTrendLabelHistory(labels,portfolio);
        }
        console.log("TRENDLABELS LENGTH",+this.trendLabels.length);
        data.forEach((strategy,i) => {
            StringStrategy="";
            let beginning = new Date(strategy.date);
            if (beginning >= startdate) {
                let strategia=new Strategy();
                strategia.setDate(strategy.date);

                if(this.trendLabels.length!=0){
                    let diff=this.trendLabels[k].endvalue-this.trendLabels[k].startvalue;
                    let diffString="";
                    if(diff>0){
                        diffString = " + $"+diff.toFixed(2);
                    }
                    else diffString = "$"+diff.toFixed(2);

                    StringStrategy='{"category": "'+strategy.date+' ('+diffString+')",';
                }else{
                    StringStrategy='{"category": "'+strategy.date+'",';
                }

                strategy.list.forEach((assetClass,i) => {
                    let assetclassstrategy= new AssetClassStrategy(assetClass.percentage,assetClass.id,assetClass.name);
                    StringStrategy=StringStrategy+'"'+assetClass.name+'":'+assetClass.percentage;
                    if(i!= strategy.list.length-1){
                        StringStrategy=StringStrategy+',';
                    }
                    strategia.addAssetClassStrategy(assetclassstrategy);
                });
                StringStrategy=StringStrategy+'}';
                JSONStrategy=JSON.parse(StringStrategy);
                this.chartData[k]=JSONStrategy;
                this.strategies.addStrategy(strategia);
                k++;
            }


        });

        for(let i=0;i<this.strategies.dataclass.length;i++){
            let singlegraph={
                "balloonText": "<b>[[title]]</b><br><span style='font-size:14px'><b>[[value]]%</b></span>",
                "fillAlphas": 0.8,
                "labelText": "[[percents]]%",
                "lineAlpha": 0.3,
                "title": "",
                "type": "column",
                "color": "#000000",
                "columnWidth": 0.5,
                "valueField": "",
                "fillColors":"",
                "percentPrecision" : 0
            };
            singlegraph.fillColors=this.strategies.colorclass[i];
            singlegraph.valueField=this.strategies.dataclass[i];
            singlegraph.title=this.strategies.dataclass[i][0].toUpperCase( )+this.strategies.dataclass[i].slice(1);
            this.graphs.push(singlegraph);
        }
        console.log("GRAPHS",this.graphs);

        this.options={
            "type": "serial",
            "startDuration": 1,
            "backgroundAlpha": 1,
            "backgroundColor": "#ffffff",
            // "legend": {
            //     "horizontalGap": 10,
            //     "useGraphSettings": true,
            //     "markerSize": 10
            // },
            "dataProvider": this.chartData,
            "valueAxes": [ {
                "stackType": "100%",
                "axisAlpha": 0,
                "gridAlpha": 0,
            } ],
            "graphs": this.graphs,
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
