import { NgModule } from '@angular/core';
import { ChartsModule } from 'ng2-charts/ng2-charts';
import { DropdownModule } from 'ng2-bootstrap/dropdown';
import { ProgressbarModule } from 'ng2-bootstrap/progressbar';
import { CommonModule } from '@angular/common';
import { BacktestingComponent } from './backtesting.component';
import { BacktestingRoutingModule } from './backtesting-routing.module';
import { HttpModule,JsonpModule } from '@angular/http';
import { AccordionModule} from 'primeng/primeng';     //accordion and accordion tab
import { FormsModule } from '@angular/forms';
import { AmChartsModule } from "amcharts3-angular2";

import {CalendarModule} from 'primeng/primeng';

import { SharedModuleModule } from "../shared/shared-module/shared-module.module";
import { DashboardModule } from "../dashboard/dashboard.module"


@NgModule({
  imports: [
    BacktestingRoutingModule,
    ChartsModule,
    DropdownModule,
      ProgressbarModule,
    CommonModule,
    HttpModule,
    JsonpModule,
    AccordionModule,
    FormsModule,
    CalendarModule,
    AmChartsModule,
    SharedModuleModule,
      DashboardModule
  ],
  declarations: [
    BacktestingComponent
  ]
})
export class BacktestingModule { }
