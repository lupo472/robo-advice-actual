import { NgModule } from '@angular/core';
import { ChartsModule } from 'ng2-charts/ng2-charts';
import { DropdownModule } from 'ng2-bootstrap/dropdown';
import { CommonModule } from '@angular/common';
import { ForecastingComponent } from './forecasting.component';
import { ForecastingRoutingModule } from './forecasting-routing.module';
import { HttpModule,JsonpModule } from '@angular/http';
import { AccordionModule} from 'primeng/primeng';     //accordion and accordion tab
import { FormsModule } from '@angular/forms';
import { AmChartsModule } from "amcharts3-angular2";
import {ProgressBarModule} from 'primeng/primeng';

import {CalendarModule} from 'primeng/primeng';

import { SharedModuleModule } from "../shared/shared-module/shared-module.module";
import { DashboardModule } from "../dashboard/dashboard.module"


@NgModule({
  imports: [
    ForecastingRoutingModule,
    ChartsModule,
    DropdownModule,
    CommonModule,
    HttpModule,
    JsonpModule,
    AccordionModule,
    FormsModule,
    CalendarModule,
    AmChartsModule,
    SharedModuleModule,
    DashboardModule,
      ProgressBarModule
  ],
  declarations: [
    ForecastingComponent
  ]
})
export class ForecastingModule { }
