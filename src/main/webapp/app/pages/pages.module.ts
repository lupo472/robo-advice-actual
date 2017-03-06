import {NgModule, OnInit} from '@angular/core';

import { p404Component } from './404.component';
import { p500Component } from './500.component';
import { LoginComponent } from './login.component';
import { RegisterComponent } from './register.component';


import { PagesRoutingModule } from './pages-routing.module';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { AlertModule } from 'ng2-bootstrap/alert';

@NgModule({
  imports: [ PagesRoutingModule, CommonModule, FormsModule,AlertModule ],
  declarations: [
    p404Component,
    p500Component,
    LoginComponent,
    RegisterComponent,
  ]
})
export class PagesModule{ }
