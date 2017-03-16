import {AssetService} from '../../services/asset.service';
import {UserService} from '../../services/user.service';
import {Component, OnInit, ViewChild} from '@angular/core';
import {BaseChartDirective} from "ng2-charts";

@Component({
    selector: 'app-strategy-classes-graph',
    templateUrl: './strategy-classes-graph.component.html',
    styleUrls: ['./strategy-classes-graph.component.scss']
})
export class StrategyClassesGraphComponent implements OnInit {

    @ViewChild(BaseChartDirective) chart: BaseChartDirective;

    constructor(private AssetService: AssetService, private UserService: UserService) {
    }

    login: any;
    public period = 30;
    public today:Date = new Date();
    public startdate:Date;
    public data:any = {};

    public assetClassDatasets = [];
    public assetClassLabels = [];

    public render: boolean = false;
    public response: string = 'Data not yet available';

    ngOnInit() {
        this.login = this.UserService.getLogin();
        this.AssetService.getPortfolioForPeriod(this.period).subscribe(res => this.getPortfolio(res));
    }

    getPortfolio(res) {
        if (res.response == 1) {
            this.data = res.data;

            this.refreshChart();

            this.render = true;
        }
    }

    setPeriod() {
        let timeDiff = Math.abs(this.today.getTime() - this.startdate.getTime());
        this.period = Math.ceil(timeDiff / (1000 * 3600 * 24));

        this.AssetService.getPortfolioForPeriod(this.period).subscribe(res => this.getPortfolio(res));
    }

    refreshChart() {
        setTimeout(() => {

            if (this.chart.chart.config.data) {
                this.chart.chart.config.data.labels = this.data.labels;
                this.chart.chart.config.data.datasets = this.data.datasets;
                this.chart.chart.update();
            }
        });
    }

    // events
    public chartClicked(e: any): void {
        console.log(e);
    }

    public chartHovered(e: any): void {
        console.log(e);
    }

    // lineChart
    public lineChartOptions: any = {
        animation: false,
        responsive: true,
        legend: true
    };

    public lineChartType: string = 'line';
    public assetClassType: string = 'bar';
}
