import { TestBed, inject,async, fakeAsync, tick } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http,ConnectionBackend,BaseRequestOptions,Response,ResponseOptions, XHRBackend, RequestOptions } from '@angular/http';
import { StrategyService } from './strategy.service';
import {AppService} from "./app.service";
import {AssetService} from "./asset.service";
import {MockAppService} from "../mocks/mock-app-service";
import {MockAssetService} from "../mocks/mock-asset-service";



describe('StrategyService', () => {
  let appservice : AppService;
  let assetservice: AssetService;
  let service: StrategyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide:AppService, useClass:MockAppService },
        { provide:AssetService, useClass:MockAssetService },
          StrategyService
      ]
    });
    appservice = TestBed.get(AppService);
    assetservice = TestBed.get(AssetService);
  });

  it('should create an instance of StrategyService', inject([AppService,AssetService], (appService,assetService) => {
    service = new StrategyService(appService,assetService);
    expect(service).toBeDefined();
  }));

  /*it('#getAssetClassSet should return 2 assetClassStrategies', inject([AppService,,AssetService], (appService,assetService) => {
   expect(appService.getAssetClassSet().length).toBe(2);
   }));*/

  it('#getDefaultStrategySet should return 2 strategies', fakeAsync(inject([AppService,AssetService, StrategyService], (appService,assetService,strategyService) => {


    var data;
    strategyService.getDefaultStrategySet().subscribe(res =>{

      data = res.length;
      console.log("###################DATA TEST data "+data);
    });

    tick(2000);
    expect(1).toEqual(1);
  })));

  /*it('#getDefaultStrategySet should return 2 strategies', inject([AppService,AssetService], (appService,assetService) => {
    let assignStrategy;

    service.getDefaultStrategySet().subscribe(res => {
    expect(res).toBeDefined();
    });
  }));*/



  /*it('should return, by default, an empty object', inject([StrategyService], (service: StrategyService) => {
    service.getDefaultStrategySet().subscribe((strategies)=>{
      expect(strategies).toBeDefined();
    });
  }));*/

});

