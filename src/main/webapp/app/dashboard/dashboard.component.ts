import { Component, OnInit } from '@angular/core';

import {UserService} from "../services/user.service";
import {AssetService} from "../services/asset.service";

@Component({
  templateUrl: 'dashboard.component.html'
})
export class DashboardComponent implements OnInit {

  constructor(private UserService:UserService, private AssetService:AssetService) { }

  private login: any;
  public period:number = 0;

  public render: boolean = false;

  public data:any = {};

  ngOnInit() {
    this.login = this.UserService.getLogin();
    this.AssetService.getPortfolioForPeriod(this.period).subscribe(res => this.getPortfolio(res));
  }

  getPortfolio(res) {
    if (res.response == 1) {
      this.data = res.data;

      this.render = true;
    }
  }
}
