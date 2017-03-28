import {AssetService} from '../../services/asset.service';
import {UserService} from '../../services/user.service';
import {Component, OnInit, ViewChild, Input} from '@angular/core';
import {BaseChartDirective} from "ng2-charts";

@Component({
    selector: 'app-strategy-classes-graph',
    templateUrl: './strategy-classes-graph.component.html',
    styleUrls: ['./strategy-classes-graph.component.scss']
})
export class StrategyClassesGraphComponent implements OnInit {

    @Input() data;
    @Input() render;
    @ViewChild(BaseChartDirective) chart: BaseChartDirective;

    constructor(private AssetService: AssetService, private UserService: UserService) {
    }

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

        currentlabels.forEach((item, index) => {
            let date = new Date(item);
            console.log(date);
            if(date > this.startdate){
                dataselect.datasets.push(currentdatasets[index]);
                dataselect.labels.push(currentlabels[index]);
            }
        });

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

    public chartHovered(e: any): void {
        console.log(e);
    }

    // lineChart
    public lineChartOptions: any = {
        animation: false,
        responsive: true
    };

    public lineChartType: string = 'line';
    public legend: boolean = false;
}
