import {AssetClassStrategy} from "./asset-class-strategy";
import {Strategy} from "./strategy";
export class MyActiveStrategyAmChart {
    private options;
    private strategy:Strategy = new Strategy();
    private dataProvider: any[] = [];
    private labels:string[] = [];
    private maxValue;
    private bandElement = {};
    private bands = [];
    private innerRadius = 100;
    private radius = 85;
    private bandBackground = {};

    constructor(data) {
        this.maxValue = 60;
        console.log("d", data);
        if (data) {
            data.list.forEach((item) => {
               // let asset = new AssetClassStrategy(item.percentage,item.id,item.name);
            this.strategy.addAssetClassStrategy(new AssetClassStrategy(item.percentage,item.id,item.name));
            // this.bandBackground ={
            //     "color": "#eee",
            //     "startValue": 0,
            //     "endValue": 100,
            //     "radius": this.radius,
            //     "innerRadius": this.innerRadius
            // };
            // this.bandElement = {
            //     "color": asset.assignColour(),
            //     "startValue": 0,
            //     "endValue": asset.getPercentage(),
            //     "radius": this.radius,
            //     "innerRadius": this.innerRadius,
            //     "balloonText": asset.getPercentage().toString() + '%'
            // }
            // this.radius = this.radius -20;
            // this.innerRadius = this.innerRadius -20;
            // this.bands.push(this.bandBackground);
            // this.bands.push(this.bandElement);

        });
        }
        /*console.log("bands",this.bands);
        console.log("st",this.strategy);*/
        let array = this.strategy.getStrategyArray();
        console.log("array",array);
        this.options = {
            "type": "gauge",
            "theme": "none",
            "axes": [{
                "axisAlpha": 0,
                "tickAlpha": 0,
                "labelsEnabled": false,
                "startValue": 0,
                "endValue": this.maxValue,
                "startAngle": 0,
                "endAngle": 270,
                "bands":
                [{
                    "color": "#eee",
                    "startValue": 0,
                    "endValue": this.maxValue,
                    "radius": "100%",
                    "innerRadius": "85%"
                }, {
                    "color": array[0].assignColour(),
                    "startValue": 0,
                    "endValue": array[0].getPercentage(),
                    "radius": "100%",
                    "innerRadius": "85%",
                    "balloonText": array[0].getPercentage().toString() + '%'
                }, {
                    "color": "#eee",
                    "startValue": 0,
                    "endValue": this.maxValue,
                    "radius": "80%",
                    "innerRadius": "65%"
                }, {
                    "color": array[1].assignColour(),
                    "startValue": 0,
                    "endValue": array[1].getPercentage(),
                    "radius": "80%",
                    "innerRadius": "65%",
                    "balloonText": array[1].getPercentage().toString() + '%'
                }, {
                    "color": "#eee",
                    "startValue": 0,
                    "endValue": this.maxValue,
                    "radius": "60%",
                    "innerRadius": "45%"
                }, {
                    "color": array[2].assignColour(),
                    "startValue": 0,
                    "endValue": array[2].getPercentage(),
                    "radius": "60%",
                    "innerRadius": "45%",
                    "balloonText": array[2].getPercentage().toString() + '%'
                }, {
                    "color": "#eee",
                    "startValue": 0,
                    "endValue": this.maxValue,
                    "radius": "40%",
                    "innerRadius": "25%"
                }, {
                    "color": array[3].assignColour(),
                    "startValue": 0,
                    "endValue": array[3].getPercentage(),
                    "radius": "40%",
                    "innerRadius": "25%",
                    "balloonText": array[3].getPercentage().toString() + '%'
                }]
            }],
            "allLabels": [{
                "text": array[0].getName(),
                "x": "49%",
                "y": "16%",
                "size": 15,
                "bold": true,
                "color": array[0].assignColour(),
                "align": "right"
            }, {
                "text": array[1].getName(),
                "x": "49%",
                "y": "23%",
                "size": 15,
                "bold": true,
                "color": array[1].assignColour(),
                "align": "right"
            }, {
                "text": array[2].getName(),
                "x": "49%",
                "y": "30%",
                "size": 15,
                "bold": true,
                "color": array[2].assignColour(),
                "align": "right"
            }, {
                "text": array[3].getName(),
                "x": "49%",
                "y": "37%",
                "size": 15,
                "bold": true,
                "color": array[3].assignColour(),
                "align": "right"
            }],
            "export": {
                "enabled": false
            }
        }
        console.log("finalbands",this.options.axes[0].bands);
    }
    getOptions(){
        return this.options;
    }
    changeChart(dataProvider?,color?){
        this.options = JSON.parse(JSON.stringify(this.options));
        return this.options;
    }
}
