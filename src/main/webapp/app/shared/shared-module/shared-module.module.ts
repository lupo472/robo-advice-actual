import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {DetailMarketValueChartComponent} from "../../edit/detail-market-value-chart/detail-market-value-chart.component";
import { AmChartsModule } from "amcharts3-angular2";
import {TabMenuModule,MenuItem} from 'primeng/primeng';
@NgModule({
  imports: [
    CommonModule,
    AmChartsModule,
      TabMenuModule
  ],
  declarations: [
      DetailMarketValueChartComponent
  ],
  exports:[
      DetailMarketValueChartComponent,
      CommonModule
  ]
})
export class SharedModuleModule { }
