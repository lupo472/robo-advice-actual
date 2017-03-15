import { Component, OnInit, Renderer } from '@angular/core';
import { Router } from '@angular/router';
import { AssetService } from '../services/asset.service';
import { StrategyService } from '../services/strategy.service';
import { AppService } from '../services/app.service';
import { UserService } from '../services/user.service';
import { Cookie } from 'ng2-cookies';
import { PortfolioElem } from '../model/portfolioelem'
import {Login} from '../model/login';

@Component({
  templateUrl: 'dashboard.component.html'
})
export class DashboardComponent implements OnInit {

  constructor() { }

  ngOnInit() { }

}
