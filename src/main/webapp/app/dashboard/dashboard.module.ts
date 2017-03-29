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
import { PortfolioGraphComponent } from './portfolio-graph/portfolio-graph.component';
import { HystoryStrategyComponent } from './hystory-strategy/hystory-strategy.component';
import {CalendarModule} from 'primeng/primeng';
import { HistoryChartComponent } from './history-chart/history-chart.component';

import {SharedModuleModule} from "../shared/shared-module/shared-module.module";
import { TabMenuComponent } from './tab-menu/tab-menu.component';
import {StrategySelectorComponent} from "../edit/strategy-selector/strategy-selector.component";


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
    PortfolioGraphComponent,
    HystoryStrategyComponent,
    HistoryChartComponent,
    TabMenuComponent
  ],
  exports: [
    PortfolioGraphComponent
  ]
})
export class DashboardModule { }
