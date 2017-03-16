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
import { HystoryStrategyComponent } from './hystory-strategy/hystory-strategy.component';
import {CalendarModule} from 'primeng/primeng';


@NgModule({
  imports: [
    DashboardRoutingModule,
    ChartsModule,
    DropdownModule,
    CommonModule,
    HttpModule,
    JsonpModule,
    AccordionModule,
    FormsModule,
    CalendarModule
  ],
  declarations: [
    DashboardComponent,
    StrategyGraphComponent,
    StrategyClassesGraphComponent,
    HystoryStrategyComponent
  ]
})
export class DashboardModule { }
