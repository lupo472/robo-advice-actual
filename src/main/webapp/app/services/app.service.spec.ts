import { async, getTestBed,TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { AppService } from './app.service';
import { MockBackend, MockConnection } from '@angular/http/testing';
import { Http,ConnectionBackend,BaseRequestOptions,Response,ResponseOptions, XHRBackend } from '@angular/http';
import {IStrategy} from "../model/interfaces/istrategy";
import {AppConfig} from "./app.config";
import {IAssetClassStrategy} from "../model/interfaces/iasset-class-strategy";
import {StrategiesMock} from '../mocks/strategies-mock';
import {Strategy} from "../model/strategy";
import {IDefaultStrategy} from "../model/interfaces/idefault-strategy";

describe('Service:AppService', () => {
  let strategiesMock = new StrategiesMock();
  beforeEach(() => {

    TestBed.configureTestingModule({
      providers: [
        BaseRequestOptions,
        MockBackend,
        AppService,
        {
          deps: [
            MockBackend,
            BaseRequestOptions
          ],
          provide: Http,
          useFactory: (backend: XHRBackend, defaultOptions: BaseRequestOptions) => {
            return new Http(backend, defaultOptions);
          }
        }
      ]
    });
  });

  function setupConnections(backend: MockBackend, options: any,action:string) {
    backend.connections.subscribe((connection: MockConnection) => {
      expect(connection.request.url).toBe(AppConfig.url + action);
        const responseOptions = new ResponseOptions(options);
        const response = new Response(responseOptions);
        connection.mockRespond(response);
    });
  }
  it('should should return the list of strategies', inject([AppService, MockBackend], (service:AppService, backend) => {
      setupConnections(backend,{
        body:{
          data:strategiesMock
        },
        status:200
      },'getDefaultStrategySet');
      service.getDefaultStrategySet().subscribe((data:IDefaultStrategy[])=>{
        expect(data.length).toBe(5);
        expect(data[0].name).toBe('Bounds');
        expect(data[1].name).toBe('Income');
        expect(data[2].name).toBe('Balanced');
      });
  }));
  it('should log an error to the console on error', inject([AppService, MockBackend], (service:AppService, backend) => {
    setupConnections(backend, {
      body: { error: `I'm afraid I've got some bad news!` },
      status: 500
    },'getDefaultStrategySet');
    spyOn(console, 'error');

    service.getDefaultStrategySet().subscribe(null, () => {
      expect(console.error).toHaveBeenCalledWith(`I'm afraid I've got some bad news!`);
    });
  }));
  it('should should return the list of asset class strategies', inject([AppService, MockBackend], (service:AppService, backend) => {
    setupConnections(backend,{
      body: {
        data: [
          {
            id: 1,
            name: 'bonds',
            percentage:95
          },
          {
            id: 4,
            name: 'commodities',
            percentage:5
          }
        ]
      },
      status: 200
    },'getAssetClassSet');
    service.getAssetClassSet().subscribe((data:IAssetClassStrategy[])=>{
      expect(data.length).toBe(2);
      expect(data[0].name).toBe('bonds');
      expect(data[1].name).toBe('commodities');
    });
  }));
});
