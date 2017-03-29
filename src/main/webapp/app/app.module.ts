import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';

import { AppComponent } from './app.component';
import { DropdownModule } from 'ng2-bootstrap/dropdown';
import { AlertModule } from 'ng2-bootstrap/alert';
import { TabsModule } from 'ng2-bootstrap/tabs';
import { ProgressbarModule } from 'ng2-bootstrap/progressbar';
import { CarouselModule } from 'ng2-bootstrap';

import { NAV_DROPDOWN_DIRECTIVES } from './shared/nav-dropdown.directive';
import { Cookie } from 'ng2-cookies';

import { ChartsModule } from 'ng2-charts/ng2-charts';
import { SIDEBAR_TOGGLE_DIRECTIVES } from './shared/sidebar.directive';
import { AsideToggleDirective } from './shared/aside.directive';
import { BreadcrumbsComponent } from './shared/breadcrumb.component';
//import { AmChartsModule } from "amcharts3-angular2";
//D3
import { D3Service } from 'd3-ng2-service';

// Routing Module
import { AppRoutingModule } from './app.routing';

// Layouts
import { FullLayoutComponent } from './layouts/full-layout.component';
import { SimpleLayoutComponent } from './layouts/simple-layout.component';
import { HttpModule } from '@angular/http';

//Services
import { StrategyService } from './services/strategy.service';
import { AppService } from './services/app.service';
import { UserService } from './services/user.service';
import { AssetService } from './services/asset.service';
import { AmChartsModule } from "amcharts3-angular2";
import { SplashComponent } from './splash/splash.component';


@NgModule({
  imports: [
    BrowserModule,
    AppRoutingModule,
    DropdownModule.forRoot(),
    TabsModule.forRoot(),
      ProgressbarModule.forRoot(),
    ChartsModule,
    HttpModule,
    AlertModule,
      CarouselModule
  ],
  exports:[
    AmChartsModule
  ],
  declarations: [
    AppComponent,
    FullLayoutComponent,
    SimpleLayoutComponent,
    NAV_DROPDOWN_DIRECTIVES,
    BreadcrumbsComponent,
    SIDEBAR_TOGGLE_DIRECTIVES,
    AsideToggleDirective,
    SplashComponent

  ],
  providers: [
    StrategyService,
    D3Service,
    {
    provide: LocationStrategy,
    useClass: HashLocationStrategy
  },
    AppService,
    UserService,
    AssetService,
    Cookie],

  bootstrap: [ AppComponent ]
})
export class AppModule {


}
