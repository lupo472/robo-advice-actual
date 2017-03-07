import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StrategyAssetsTableComponent } from './strategy-assets-table.component';

describe('StrategyAssetsTableComponent', () => {
  let component: StrategyAssetsTableComponent;
  let fixture: ComponentFixture<StrategyAssetsTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StrategyAssetsTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StrategyAssetsTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
