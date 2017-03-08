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
  
  constructor(private AppService:AppService,private UserService:UserService,private AssetService:AssetService,private router:Router,public StrategyService:StrategyService){ }

  user:any;
  public portfolio = [];
  public daybyday = [];
  
  ngOnInit(): void {
      this.user = this.UserService.getUser();
      this.AppService.getUserPortfolioPeriod(this.user.id,30).subscribe(res => this.getPortfolio(res));
  }
  
  getPortfolio(res){
    this.portfolio = res.data;
    
    this.portfolio.forEach((item, index)=>{
      
      var portfolioElem = item.list;
      
      var label = [];
      var value = [];
      var percentage = [];
      
      portfolioElem.forEach((element,i)=>{
        label[i] = element.assetClassStrategy.assetClass.name;
        percentage[i] = element.assetClassStrategy.percentage;
        value[i] = element.value;
      })
      
      this.daybyday[index] = {date: item.date, 
                              value: value,
                              label: label,
                              percentage: percentage};
    })
    
    console.dir(this.daybyday);
  }
  
}
