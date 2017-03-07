import { Component, OnInit } from '@angular/core';

import { AssetService } from '../../services/asset.service';

@Component({
  selector: 'app-strategy-assets-table',
  templateUrl: './strategy-assets-table.component.html',
  styleUrls: ['./strategy-assets-table.component.scss']
})
export class StrategyAssetsTableComponent implements OnInit {
  
  assetList = [];

  constructor(private AssetService:AssetService) {
    this.AssetService.getAssetClassSet().subscribe(res=> this.getAssetClass(res));
    this.AssetService.getUserCurrentPortfolio().subscribe(res=> this.getCurrentPortfolio(res));
   }
  
  getAssetClass(res){
    console.log("ASSET CLASS SUB: " + JSON.stringify(res));
  }
  
  getCurrentPortfolio(res){
    console.log("PORTFOLIO SUB: " + JSON.stringify(res));
    
    this.assetList = res;
  }

  ngOnInit() {
  }

}
