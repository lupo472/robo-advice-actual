import {Component, OnInit, ViewChild} from '@angular/core';
import {BaseChartDirective} from "ng2-charts";
import {StrategyService} from "../../services/strategy.service";
import {forEach} from "@angular/router/src/utils/collection";

@Component({
    selector: 'app-hystory-strategy',
    templateUrl: './hystory-strategy.component.html',
    styleUrls: ['./hystory-strategy.component.scss']
})
export class HystoryStrategyComponent implements OnInit {

    @ViewChild(BaseChartDirective) chart: BaseChartDirective;

    public period = 30;
    public startdate: Date;
    public maxdate: Date;
    public chartData = [];
    public labels = [];
    public dati = [];
    public render: boolean = false;
    public trendLabels= [];


    constructor(private StrategyService: StrategyService) {
        this.maxdate = new Date();
        this.startdate = new Date();
        console.log("today" + this.maxdate);
        this.startdate.setDate(this.startdate.getDate() - this.period);
    }

    ngOnInit() {
        this.StrategyService.getHistoryStrategies().subscribe(res => this.getStrategies(res));
    }

    refreshData() {
        this.dati = [];
        this.chartData = [];
        this.labels = [];
        let res = this.StrategyService.refreshHistory(this.startdate);
        this.getStrategies(res);
    }

    getStrategies(res) {
        if (res) {
            this.chartData = res.data;
            this.labels = res.labels;
            this.trendLabels=this.StrategyService.createTrendLabelHistory(this.labels);
            this.labels.forEach((label,i)=>{
                let diff:number=this.trendLabels[i].endvalue-this.trendLabels[i].startvalue;
                console.log("diff", diff.toFixed(2));
                if(diff>0){
                    this.labels[i] = label+ " ("+"+ $"+diff.toFixed(2)+" )";
                }
                else this.labels[i] = label+ " ($ "+diff.toFixed(2)+" )";
            });
            console.log("NEW LABELS" ,this.labels);
            this.refreshChart();
            this.render = true;
            console.log("history",this.StrategyService.historyStrategies);
        }
    }

    refreshChart() {
        setTimeout(() => {

            if (this.chart.chart.config.data) {
                this.chart.chart.config.data.labels = this.labels;
                this.chart.chart.config.data.datasets = this.chartData;
                this.chart.chart.update();
            }
        });
    }

    // barChart
    public barChartOptions: any = {
        scaleShowVerticalLines: false,
        responsive: true,
        scales: {
            yAxes: [{
                display: true,
                ticks: {
                    display: true,
                    min: 0,
                    max: 100,
                }
            }],
        }
    };

    public barChartType: string = 'bar';
    public barChartLegend: boolean = false;

}
