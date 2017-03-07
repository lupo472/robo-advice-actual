import { NgModule } from '@angular/core';
import { ChartsModule } from 'ng2-charts/ng2-charts';
import { DropdownModule } from 'ng2-bootstrap/dropdown';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from './dashboard.component';
import { DashboardRoutingModule } from './dashboard-routing.module';

import { HttpModule,JsonpModule } from '@angular/http';

import {AccordionModule} from 'primeng/primeng';     //accordion and accordion tab
import { FormsModule } from '@angular/forms';
import { StrategyGraphComponent } from './strategy-graph/strategy-graph.component';
import { StrategyClassesGraphComponent } from './strategy-classes-graph/strategy-classes-graph.component';
import { StrategyAssetsTableComponent } from './strategy-assets-table/strategy-assets-table.component'


@NgModule({
  imports: [
    DashboardRoutingModule,
    ChartsModule,
    DropdownModule,
    CommonModule,
    HttpModule,
    JsonpModule,
    AccordionModule,
    FormsModule
  ],
  declarations: [
    DashboardComponent,
    StrategyGraphComponent,
    StrategyClassesGraphComponent,
    StrategyAssetsTableComponent
  ]
})
export class DashboardModule { }
