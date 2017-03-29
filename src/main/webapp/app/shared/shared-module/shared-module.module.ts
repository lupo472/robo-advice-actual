import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {DetailMarketValueChartComponent} from "../../edit/detail-market-value-chart/detail-market-value-chart.component";
import { AmChartsModule } from "amcharts3-angular2";
import { TabsModule } from 'ng2-bootstrap/tabs';
import {TabMenuModule,MenuItem} from 'primeng/primeng';
import {StrategySelectorComponent} from "../../edit/strategy-selector/strategy-selector.component";
@NgModule({
  imports: [
    CommonModule,
    AmChartsModule,
      TabMenuModule,
      TabsModule
  ],
  declarations: [
      DetailMarketValueChartComponent
  ],
  exports:[
      DetailMarketValueChartComponent,
      CommonModule,
      TabMenuModule,
      TabsModule
  ]
})
export class SharedModuleModule { }
