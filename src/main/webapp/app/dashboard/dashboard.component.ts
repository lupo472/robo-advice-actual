import { Component, OnInit, Renderer } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { AssetClass } from './assetclass';

@Component({
  templateUrl: 'dashboard.component.html',
  providers:[UserService]
})
export class DashboardComponent implements OnInit {
    
  constructor(){ }

  //ASSET CLASS COLOUR//
  public assetClassColour:string =  '#20a8d8';
  public assetClass1Colour:string =  '#4dbd74';
  public assetClass2Colour:string =   '#63c2de';
  public assetClass3Colour:string =  '#f8cb00';
  public assetClass4Colour:string =   '#f86c6b';

  public assetClass:number = 0;

  //CHANGE ASSET CLASS VIEW
  public showAsset(value){
    this.assetClass = value;
    console.log("assetClass = " + this.assetClass);
  }

  // dropdown buttons
  public status: { isopen: boolean } = { isopen: false };
  public toggleDropdown($event:MouseEvent):void {
    $event.preventDefault();
    $event.stopPropagation();
    this.status.isopen = !this.status.isopen;
  }

  //convert Hex to RGBA
  public convertHex(hex:string,opacity:number){
    hex = hex.replace('#','');
    let r = parseInt(hex.substring(0,2), 16);
    let g = parseInt(hex.substring(2,4), 16);
    let b = parseInt(hex.substring(4,6), 16);

    let rgba = 'rgba('+r+','+g+','+b+','+opacity/100+')';
    return rgba;
  }

  // events
  public chartClicked(e:any):void {
    console.log(e);
  }

  public chartHovered(e:any):void {
    console.log(e);
  }
  
  //ASSET CLASS
  public assetClasses:Array<any> = [
    {name: 'Bonds',       data: [65, 59, 84, 84, 51, 55, 40], percentage: 15},
    {name: 'Forex',       data: [65, 59, 84, 84, 51, 55, 40], percentage: 15},
    {name: 'Stocks',      data: [65, 59, 84, 84, 51, 55, 40], percentage: 15},
    {name: 'Commodities', data: [65, 59, 84, 84, 51, 55, 40], percentage: 15}
  ];
  
  
  //ASSET CLASS 1
  public assetClass1:Array<any> = [
    {name: 'CHRIS/CME_US1', data: [65, 59, 84, 84, 51, 55, 40], percentage: 15},
    {name: 'CHRIS/CME_UL1', data: [65, 59, 84, 84, 51, 55, 40], percentage: 15}
  ];
  
  //ASSET CLASS 2
  public assetClass2:Array<any> = [
    {name: 'CURRFX/USDEUR', data: [65, 59, 84, 84, 51, 55, 40], percentage: 15},
    {name: 'CURRFX/USDCHF', data: [65, 59, 84, 84, 51, 55, 40], percentage: 15},
    {name: 'BAVERAGE/USD',  data: [65, 59, 84, 84, 51, 55, 40], percentage: 15}
  ];
  
  //ASSET CLASS 3
  public assetClass3:Array<any> = [
    {name: 'WIKI/FB',   data: [65, 59, 84, 84, 51, 55, 40], percentage: 15},
    {name: 'WIKI/AAPL', data: [65, 59, 84, 84, 51, 55, 40], percentage: 15},
    {name: 'WIKI/MSFT', data: [65, 59, 84, 84, 51, 55, 40], percentage: 15},
    {name: 'WIKI/TWTR', data: [65, 59, 84, 84, 51, 55, 40], percentage: 15}
  ];
  
  //ASSET CLASS 4
  public assetClass4:Array<any> = [
    {name: 'COM/WLD_GOLD',    data: [65, 59, 84, 84, 51, 55, 40], percentage: 15},
    {name: 'COM/WLD_SILVER',  data: [65, 59, 84, 84, 51, 55, 40], percentage: 15},
    {name: 'COM/OIL_BRENT',   data: [65, 59, 84, 84, 51, 55, 40], percentage: 15},
    {name: 'COM/WLD_RICE_05', data: [65, 59, 84, 84, 51, 55, 40], percentage: 15}
  ];
  
  //LINECHART GENERAL
  public lineChartLabels:Array<any> = ['January', 'February', 'March', 'April', 'May', 'June', 'July'];
  public lineChartOptions:any = {
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
  public lineChartType:string = 'line';
  public lineChartColours:Array<any> = [
    { // grey
      backgroundColor: this.assetClassColour,
      borderColor: 'rgba(255,255,255,.55)'
    }
  ];
  
  // barChart1
  public barChart1Data:Array<any> = [
    {
      data: [78, 81, 80, 45, 34, 12, 40, 78, 81, 80, 45, 34, 12, 40, 12, 40],
      label: 'Series A'
    }
  ];
  public barChart1Labels:Array<any> = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16'];
  public barChart1Options:any = {
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
  public barChart1Colours:Array<any> = [
    {
      backgroundColor: 'rgba(255,255,255,.3)',
      borderWidth: 0
    }
  ];
  public barChart1Legend:boolean = false;
  public barChart1Type:string = 'bar';

  ngOnInit(): void {

    }
  }
