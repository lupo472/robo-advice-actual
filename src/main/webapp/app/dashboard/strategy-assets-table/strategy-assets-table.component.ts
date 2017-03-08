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
    
  }
  
  getCurrentPortfolio(res){
    this.assetList = res;
  }

  ngOnInit() {
  }

}
