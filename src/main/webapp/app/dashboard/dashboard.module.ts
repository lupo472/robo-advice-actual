import { NgModule } from '@angular/core';
import { ChartsModule } from 'ng2-charts/ng2-charts';
import { DropdownModule } from 'ng2-bootstrap/dropdown';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from './dashboard.component';
import { DashboardRoutingModule } from './dashboard-routing.module';
import { HttpModule,JsonpModule } from '@angular/http';
import {AccordionModule} from 'primeng/primeng';     //accordion and accordion tab
import { FormsModule } from '@angular/forms';
import { AmChartsModule } from "amcharts3-angular2";


import { StrategyGraphComponent } from './strategy-graph/strategy-graph.component';
import { StrategyClassesGraphComponent } from './strategy-classes-graph/strategy-classes-graph.component';
import { HystoryStrategyComponent } from './hystory-strategy/hystory-strategy.component';
import {CalendarModule} from 'primeng/primeng';
import { HistoryChartComponent } from './history-chart/history-chart.component';

import {SharedModuleModule} from "../shared/shared-module/shared-module.module";


@NgModule({
  imports: [
    DashboardRoutingModule,
    ChartsModule,
    DropdownModule,
    HttpModule,
    JsonpModule,
    AccordionModule,
    FormsModule,
    CalendarModule,
    AmChartsModule,
    SharedModuleModule
  ],
  declarations: [
    DashboardComponent,
    StrategyGraphComponent,
    StrategyClassesGraphComponent,
    HystoryStrategyComponent,
    HistoryChartComponent
  ],
  exports: [
    StrategyClassesGraphComponent
  ]
})
export class DashboardModule { }
