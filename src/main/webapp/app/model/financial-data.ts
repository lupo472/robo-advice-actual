import {AssetClass} from "./asset-class";
import {FinancialDataElement} from "./financial-data-element";
export class FinancialData extends AssetClass {
    private list : FinancialDataElement[];
    lineChartLabels:string[] = [];
    lineChartLegend;
    lineChartData = [];
    lineChartOptions;
    lineChartOptionsModal;
    lineChartType;
    lineChartColours;
    max:number = 0;
    min:number = 5000;
    constructor(id:number,name:string){
        super(id,name);
        this.list = [];
    }
    setFinancialData(list:FinancialDataElement[]){
        this.list = list;
    }
    addFinancialDataElement(financialDataElement:FinancialDataElement){
        this.list.push(financialDataElement);
    }
    getFinancialData(){
        return this.list;
    }
    createLineChartLabels(){
        this.list.forEach((item)=>{
            this.lineChartLabels.push(item.getDate());
        });
    }
    createLineChartData(){
        let data = [];
        this.list.forEach((item)=>{
            data.push(item.getVlaue());
            if (item.getVlaue() > this.max){this.max=item.getVlaue()}
            if (item.getVlaue() < this.min){this.min=item.getVlaue()}
        });
        this.lineChartData = [
            {
                data:data,
                label: 'Serie A'
            }
        ];
    }
    createLineChartOptions(){

        this.lineChartOptions = {
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
                    display: true,
                    gridLines: {
                        display:false
                    },
                    ticks: {
                        display: true,
                        fontSize: 8,
                        min: this.min ,
                        max: this.max ,
                        stepSize: this.max - this.min,

                    }
                }],
            },
            elements: {
                line: {
                    borderWidth: 2
                },
                point: {
                    radius: 0,
                    hitRadius: 10,
                    hoverRadius: 3,
                },
            },
            legend: {
                display: false
            }
        };
    }
    createLineChartOptionsForModal(){
        this.lineChartOptionsModal = {
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
                    display: true,
                    gridLines: {
                        display:false
                    },
                    ticks: {
                        display: true,
                        fontSize: 8,
                        min: this.min ,
                        max: this.max ,
                        stepSize: this.max - this.min,

                    }
                }],
            },
            elements: {
                line: {
                    borderWidth: 2
                },
                point: {
                    radius: 0,
                    hitRadius: 10,
                    hoverRadius: 3,
                },
            },
            legend: {
                display: false
            }
        };
    }
    createLineChartColours(){
        this.lineChartColours = [
            {
                backgroundColor: 'transparent',
                pointBackgroundColor:'white',
                borderColor: 'rgba(255,255,255,.55)'
            }
        ];
    }
    createLineChartColoursModal(){
        this.lineChartColours = [
            {
                backgroundColor: this.assignColour(),
                pointBackgroundColor:'white',
                borderColor: 'rgba(255,255,255,.55)'
            }
        ];
    }

    createLineChart(type){
        if (type == "small") {

            this.createLineChartLabels();
            this.lineChartLegend = false;
            this.createLineChartData();
            this.createLineChartOptions();
            this.lineChartType = 'line';
            this.createLineChartColours();

        } else if (type == "big") {

            this.createLineChartLabels();
            this.lineChartLegend = false;
            this.createLineChartData();
            this.createLineChartOptions();
            this.lineChartType = 'line';
            this.createLineChartColoursModal();

        }

    }


}
