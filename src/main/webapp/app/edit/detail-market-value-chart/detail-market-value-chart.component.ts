import { Component, OnInit, Input } from '@angular/core';
@Component({
  selector: 'app-detail-market-value-chart',
  templateUrl: './detail-market-value-chart.component.html',
  styleUrls: ['./detail-market-value-chart.component.scss']
})
export class DetailMarketValueChartComponent implements OnInit {
  id = "chartdiv";
  options;
  @Input() detailOptions;
  constructor() { }

  ngOnInit() {
    this.options = this.detailOptions.getOptions();
  }

  changeChart(dataProvider?,color?){
    this.options = this.detailOptions.changeChart(dataProvider,color);
  }

}
