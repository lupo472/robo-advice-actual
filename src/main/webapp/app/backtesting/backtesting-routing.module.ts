import { NgModule } from '@angular/core';
import { Routes,
     RouterModule } from '@angular/router';

import { BacktestingComponent } from './backtesting.component';

const routes: Routes = [
  {
    path: '',
    component: BacktestingComponent,
    data: {
      title: 'Backtesting'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BacktestingRoutingModule {}
