import {Strategies} from "./strategies";
import {Strategy} from "./strategy";
import {AssetClassStrategy} from "./asset-class-strategy";
export class HistoryAmChart {
    public options={};
    public chartData = [];
    public graphs= [];
    public strategies= new Strategies();
    constructor() {}

    createHistoryChartOptions(data: any, startdate: Date) {
        this.chartData = [];
        this.strategies.resetStrategies();
        this.graphs=[];
        let k:number=0;
        let StringStrategy:string="";
        let JSONStrategy;

        data.forEach(strategy => {
            StringStrategy="";
            let beginning = new Date(strategy.date);

            if (beginning >= startdate) {
                let strategia=new Strategy();
                strategia.setDate(strategy.date);

                StringStrategy='{"category": "'+strategy.date+'",';
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
                console.log("Strategies:",this.strategies);
                k++;
            }


        });
        console.log("chartData:#############",this.chartData);

        for(let i=0;i<this.strategies.dataclass.length;i++){
            let singlegraph={
                "balloonText": "<b>[[title]]</b><br><span style='font-size:14px'>[[category]]: <b>[[value]]%</b></span>",
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
            "legend": {
                "horizontalGap": 10,
                "useGraphSettings": true,
                "markerSize": 10
            },
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
