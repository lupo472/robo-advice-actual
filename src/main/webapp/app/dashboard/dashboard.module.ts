import { NgModule } from '@angular/core';
import { ChartsModule } from 'ng2-charts/ng2-charts';
import { DropdownModule } from 'ng2-bootstrap/dropdown';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from './dashboard.component';
import { DashboardRoutingModule } from './dashboard-routing.module';
import { CardAssetClassComponent } from './card-asset-class/card-asset-class.component';
import { HttpModule,JsonpModule } from '@angular/http';
import { CardAssetComponent } from './card-asset/card-asset.component';
import { StrategySelectorComponent } from './strategy-selector/strategy-selector.component';

@NgModule({
  imports: [
    DashboardRoutingModule,
    ChartsModule,
    DropdownModule,
    CommonModule,
    HttpModule,
    JsonpModule
  ],
  declarations: [ DashboardComponent, CardAssetClassComponent, CardAssetComponent, StrategySelectorComponent ]
})
export class DashboardModule { }
