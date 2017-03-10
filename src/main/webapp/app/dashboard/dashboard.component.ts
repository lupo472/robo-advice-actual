import { Component, OnInit, Renderer } from '@angular/core';
import { Router } from '@angular/router';
import { AssetService } from '../services/asset.service';
import { StrategyService } from '../services/strategy.service';
import { AppService } from '../services/app.service';
import { UserService } from '../services/user.service';
import { Cookie } from 'ng2-cookies';
import { PortfolioElem } from '../model/portfolioelem'

@Component({
  templateUrl: 'dashboard.component.html'
})
export class DashboardComponent implements OnInit {

  constructor(private AppService: AppService, private UserService: UserService, private AssetService: AssetService, private router: Router, public StrategyService: StrategyService) { }

  user: any;

  ngOnInit() {
    this.user = this.UserService.getUser();
  }

}
