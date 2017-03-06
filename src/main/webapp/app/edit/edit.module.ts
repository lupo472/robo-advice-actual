import { NgModule } from '@angular/core';
import { ChartsModule } from 'ng2-charts/ng2-charts';
import { DropdownModule } from 'ng2-bootstrap/dropdown';
import { CommonModule } from '@angular/common';
import { EditComponent } from './edit.component';
import { EditRoutingModule } from './edit-routing.module';
import { CardAssetClassComponent } from './card-asset-class/card-asset-class.component';
import { HttpModule,JsonpModule } from '@angular/http';
import { CardAssetComponent } from './card-asset/card-asset.component';

import { CardFilterComponent } from './card-filter/card-filter.component';
import {SliderModule} from 'primeng/primeng';
import {AccordionModule} from 'primeng/primeng';     //accordion and accordion tab
import { FormsModule } from '@angular/forms'
import { StrategySelectorComponent } from './strategy-selector/strategy-selector.component';

@NgModule({
  imports: [
    EditRoutingModule,
    ChartsModule,
    DropdownModule,
    CommonModule,
    HttpModule,
    JsonpModule,
    SliderModule,
    AccordionModule,
    FormsModule
  ],
  declarations: [
    EditComponent,
    CardAssetClassComponent,
    CardAssetComponent,
    CardFilterComponent,
    StrategySelectorComponent
  ]
})
export class EditModule { }
