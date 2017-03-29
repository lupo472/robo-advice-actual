import {Component, OnInit, ViewChild, Input} from '@angular/core';
import {BaseChartDirective} from "ng2-charts";

@Component({
    selector: 'app-portfolio-graph',
    templateUrl: 'portfolio-graph.component.html'
})
export class PortfolioGraphComponent implements OnInit {

    @Input() data;
    @Input() render;
    @Input() graph;
    @ViewChild(BaseChartDirective) chart: BaseChartDirective;

    constructor() { }

    public clicked:any;
    public date:Date;

    public today:Date = new Date();
    public startdate:Date;

    public response: string = 'Data not yet available';

    ngOnInit() { }

    setPeriod() {
        let dataselect = {datasets: [], labels: []};
        let currentlabels = this.data.labels;
        let currentdatasets = this.data.datasets;

        console.log("DATA TO SELECT: ", currentdatasets);

        let i = 0;

        currentlabels.forEach((item, index) => {
            let date = new Date(item);
            if(this.graph=="future"){
                if(date < this.startdate){
                    if(i == 0){
                        i = index;
                    }
                    dataselect.labels.push(currentlabels[index]);
                }
            }else{
                if (date > this.startdate) {
                    if (i == 0) {
                        i = index;
                    }
                    dataselect.labels.push(currentlabels[index]);
                }
            }
        });

        let end = currentdatasets.length;

        currentdatasets.forEach((assetclass, index)=>{
           if(index == i){
               assetclass.data.slice(index, end);
           }
        });

        dataselect.datasets = currentdatasets;

        console.log("DATASELECT: ", dataselect );

        this.refreshChart(dataselect)
    }

    refreshChart(data) {
        setTimeout(() => {

            if (this.chart.chart.config && this.chart.chart.config.data) {
                this.chart.chart.config.data.labels = data.labels;
                this.chart.chart.config.data.datasets = data.datasets;
                this.chart.chart.update();
            }
        });
    }

    // events
    public chartClicked(e: any): void {
        this.clicked = e.active[0]._index;
        this.date = new Date(this.data.labels[this.clicked]);
        console.log("CLICK: ", this.date);
    }

    // lineChart
    public lineChartOptions: any = {
        animation: false,
        responsive: true
    };

    public lineChartType: string = 'line';
    public legend: boolean = false;
}
