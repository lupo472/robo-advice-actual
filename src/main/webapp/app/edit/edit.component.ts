import {Component, OnInit, Renderer, ViewChild, AfterViewInit} from '@angular/core';
import { Router } from '@angular/router';
import { AssetService } from '../services/asset.service';
import { StrategyService } from '../services/strategy.service';
import { AppService } from '../services/app.service';
import { Cookie } from 'ng2-cookies';
import { ModalDirective } from 'ng2-bootstrap/modal/modal.component';
import { Asset } from '../model/asset';
import { AssetClass } from '../model/asset-class';
import { Strategy } from '../model/strategy';
import { AssetClassStrategy } from '../model/asset-class-strategy';
@Component({
  templateUrl: 'edit.component.html'
})

export class EditComponent implements OnInit,AfterViewInit {
  public isCustom:boolean;
  sendStrategy:any = {};
  public assetClassSet = [];
  arrayAssets:Asset[];
  public assetSet = [];
  public assets = [];
  public selectedAsset = [];
  strategies: any = [];
  asset:Asset;
  public selected = [];
  data:any=[];
  tempAsset:Asset;
  currentStrategy:any = [];

  @ViewChild('childModal') public childModal:ModalDirective;

  constructor(public AppService:AppService,public AssetService: AssetService, public StrategyService: StrategyService, private router: Router) {
    this.isCustom = false;
    this.arrayAssets = [];
  }
  public showChildModal():void {
    this.childModal.show();
  }

  public hideChildModal():void {
    this.childModal.hide();
  }
  ngAfterViewInit() {
    // viewChild is set after the view has been initialized
    this.childModal.show();

  }

  showData(){
    console.log(this.StrategyService.strategies);
    this.data.push(this.StrategyService.strategies.get(1));
    this.data.push(this.StrategyService.strategies.get(2));
    this.data.push(this.StrategyService.strategies.get(3));
    this.data.push(this.StrategyService.strategies.get(4));
    console.log(this.data);

  }

  ngOnInit(): void {
    this.AssetService.getAssetClassSet().subscribe((result) => this.getAssetClass(result));
    this.StrategyService.getDefaultStrategySet().subscribe(res => this.getStrategy(res));
  }

  showDetails() {
    console.log("clicked");
  }

  //ASSIGN STRATEGIES
  getStrategy(res) {
    this.strategies = res;
    // console.log("strategies");
    // console.log(this.strategies);
  }

  createDefaultStrategy(){
    this.sendStrategy = new Strategy();
    this.sendStrategy.setUserId(Cookie.get('id'));
    let array = [];
    this.currentStrategy.list.forEach((item,index)=>{
      let a = new AssetClassStrategy(item.getPercentage(),new AssetClass(item.assetClass.id,item.assetClass.name));
      array.push(a);
    });
    console.log(array);
    this.sendStrategy.setStrategyArray(array);
    this.AppService.setCustomStrategy(this.sendStrategy).subscribe(
      (res) => {
        console.log(res);
      });
  }
  confirmStrategy() {
    this.StrategyService.setCustomStrategy().subscribe(
      (error) => {
        console.log("errore " + error);
      });
  }


  setStrategy(i) {
    this.currentStrategy = this.strategies[i];
    // console.log("assetClassStrategy");
    // console.log(this.currentStrategy);
    if (this.currentStrategy.name != "custom") {
      console.log("diverso");
    }
    console.log(this.StrategyService.strategies);
    this.arrayAssets.forEach((item,i)=>{
      item.setPercentage(0);
      console.log(item);
      this.currentStrategy.list.forEach((element,j)=> {
        if (item.assetClass.id == element.assetClass.id) {
          item.setPercentage(element.getPercentage());
        }
      });
    });
    if (this.currentStrategy.name != "custom") {
      console.log("diverso");
      // this.tempAsset = new Asset(10,new AssetClass(3,"stocks"));
      // let color = "#4dbd74";
      // this.tempAsset.color=color;
      // assetClassStrategy.list.push(this.tempAsset);
    }
    // console.log(this.currentStrategy);
    // console.log(this.strategies);

    if (i == (this.strategies.length - 1)) {
      this.isCustom = !this.isCustom;
    }else{
      this.isCustom = false;
    }

    this.strategies.forEach((item, index) => {
      if (index == i) {
        this.selected[index] = true;
      } else {
        this.selected[index] = false;
      }
    })

  }

  //ASSIGN ASSET CLASS
  public getAssetClass(result) {
    this.AssetService.assetClassSet = [];
    result.forEach((item,i) => {
      this.asset = new Asset(0,item);
      let color = this.AssetService.assignColour(item.id);
      this.asset.setColor(color);
      this.AssetService.assetClassSet.push(this.asset);
    });
    this.arrayAssets = this.AssetService.assetClassSet;
    // console.log(this.AssetService.assetClassSet);
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
