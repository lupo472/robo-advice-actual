import { Component, OnInit, Renderer } from '@angular/core';
import { Router } from '@angular/router';
import { AssetService } from '../services/asset.service';
import { StrategyService } from '../services/strategy.service';
import { Cookie } from 'ng2-cookies';


@Component({
  templateUrl: 'dashboard.component.html',
  providers: [AssetService]
})
export class DashboardComponent implements OnInit {
  constructor(private service:AssetService,private router:Router,public StrategyService:StrategyService){


  }

  ngOnInit(): void {

  }

}
