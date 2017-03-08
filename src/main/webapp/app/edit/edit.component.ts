import { Component, OnInit, Renderer } from '@angular/core';
import { Router } from '@angular/router';
import { AssetService } from '../services/asset.service';
import { StrategyService } from '../services/strategy.service';
import { Cookie } from 'ng2-cookies';


@Component({
  templateUrl: 'edit.component.html'
})
export class EditComponent implements OnInit {
  public isCustom:boolean;
  public assetClassSet = [];
  public assetClasses = [];
  public assetSet = [];
  public assets = [];
  public selectedAsset = [];
  strategies:any;

  constructor(public AssetService:AssetService, public StrategyService:StrategyService, private router:Router){
    this.isCustom = true;
  }

  ngOnInit(): void {
    this.AssetService.getAssetClassSet().subscribe((result) => this.getAssetClass(result));
    this.StrategyService.getDefaultStrategySet().subscribe(res=> this.getStrategy(res));
  }

  showDetails() {
    console.log("clicked");
  }

  //ASSIGN STRATEGIES
  getStrategy(res){
    this.strategies = res;
    console.log(this.strategies);
  }

  setCustomStrategy() {
    this.StrategyService.setCustomStrategy().subscribe(
      (error) => {
        console.log("errore " + error);
      });
  }

  //ASSIGN ASSET CLASS
  public getAssetClass(result) {
    this.assetClassSet = result;
    this.assetClassSet.forEach((item, index) => {

    this.assetClasses[index] = {id: item.id, name:  item.name, data: [65, 59, 84, 84, 51, 55, 40], percentage: 15}
    //this.StrategyService.strategies.set(item.id, 0);

    })
  }

  //ASSET CLASS COLOUR//
  public assetClassColour: string = '#20a8d8';
  public assetClass1Colour: string = '#4dbd74';
  public assetClass2Colour: string = '#63c2de';
  public assetClass3Colour: string = '#f8cb00';
  public assetClass4Colour: string = '#f86c6b';

  public assetClass: number = 0;

  //CHANGE ASSET CLASS VIEW
  public showAsset(value) {

    this.selectedAsset = [];

    console.log(value);

    var i = 0;

    this.assets.forEach((item, index) => {
      if (item.assetClass.id == value) {
        this.selectedAsset[i] = item;
        i++;
      }

    });

  }

  // dropdown buttons
  public status: { isopen: boolean } = { isopen: false };
  public toggleDropdown($event: MouseEvent): void {
    $event.preventDefault();
    $event.stopPropagation();
    this.status.isopen = !this.status.isopen;
  }

  //convert Hex to RGBA
  public convertHex(hex: string, opacity: number) {
    hex = hex.replace('#', '');
    let r = parseInt(hex.substring(0, 2), 16);
    let g = parseInt(hex.substring(2, 4), 16);
    let b = parseInt(hex.substring(4, 6), 16);

    let rgba = 'rgba(' + r + ',' + g + ',' + b + ',' + opacity / 100 + ')';
    return rgba;
  }

  // events
  public chartClicked(e: any): void {
    console.log(e);
  }

  //LINECHART GENERAL
  public lineChartLabels: Array<any> = ['January', 'February', 'March', 'April', 'May', 'June', 'July'];
  public lineChartOptions: any = {
    maintainAspectRatio: false,
    tooltips: {
      callbacks: {
        label: function(tooltipItem) {
          return tooltipItem.yLabel;
        }
      }
    },
    scales: {
      xAxes: [{
        gridLines: {
          color: 'transparent',
          zeroLineColor: 'transparent'
        },
        ticks: {
          fontSize: 2,
          fontColor: 'transparent',
        }

      }],
      yAxes: [{
        display: false,
        ticks: {
          display: false,
          min: 40 - 5,
          max: 84 + 5,
        }
      }],
    },
    elements: {
      line: {
        borderWidth: 1
      },
      point: {
        radius: 4,
        hitRadius: 10,
        hoverRadius: 4,
      },
    },
    legend: {
      display: false
    }
  };
  public lineChartType: string = 'line';
  public lineChartColours: Array<any> = [
    { // grey
      backgroundColor: this.assetClassColour,
      borderColor: 'rgba(255,255,255,.55)'
    }
  ];

  // barChart1
  public barChart1Data: Array<any> = [
    {
      data: [78, 81, 80, 45, 34, 12, 40, 78, 81, 80, 45, 34, 12, 40, 12, 40],
      label: 'Series A'
    }
  ];
  public barChart1Labels: Array<any> = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16'];
  public barChart1Options: any = {
    maintainAspectRatio: false,
    scales: {
      xAxes: [{
        display: false,
        barPercentage: 0.6,
      }],
      yAxes: [{
        display: false
      }]
    },
    legend: {
      display: false
    }
  };
  public barChart1Colours: Array<any> = [
    {
      backgroundColor: 'rgba(255,255,255,.3)',
      borderWidth: 0
    }
  ];
  public barChart1Legend: boolean = false;
  public barChart1Type: string = 'bar';

}
