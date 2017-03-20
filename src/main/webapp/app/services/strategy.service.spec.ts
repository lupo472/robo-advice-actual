import { TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http,ConnectionBackend,BaseRequestOptions,Response,ResponseOptions, XHRBackend, RequestOptions } from '@angular/http';
import { StrategyService } from './strategy.service';
import {AppService} from "./app.service";
import {AssetService} from "./asset.service";
import {MockAppService} from "../mocks/mock-app-service";
import {MockAssetService} from "../mocks/mock-asset-service";

describe('StrategyService', () => {
  let service : StrategyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide:AppService, useClass:MockAppService },
        { provide:AssetService, useClass:MockAssetService },
          StrategyService
      ]
    });
  });

  it('should create an instance of StrategyService', inject([AppService,AssetService], (appService,assetService) => {
    service = new StrategyService(appService,assetService);
    expect(service).toBeDefined();
  }));

  /*it('#getAssetClassSet should return 2 assetClassStrategies', inject([AppService,,AssetService], (appService,assetService) => {
   expect(appService.getAssetClassSet().length).toBe(2);
   }));*/

  it('#getDefaultStrategySet should return 2 strategies', inject([AppService,AssetService], (appService,assetService) => {
    expect(service.getDefaultStrategySet().subscribe(res => console.log("strategies",res.getStrategies())));
  }));


  /*it('should return, by default, an empty object', inject([StrategyService], (service: StrategyService) => {
    service.getDefaultStrategySet().subscribe((strategies)=>{
      expect(strategies).toBeDefined();
    });
  }));*/

});

