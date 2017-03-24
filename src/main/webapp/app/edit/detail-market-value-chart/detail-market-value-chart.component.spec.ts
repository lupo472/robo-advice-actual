import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailMarketValueChartComponent } from './detail-market-value-chart.component';

describe('DetailMarketValueChartComponent', () => {
  let component: DetailMarketValueChartComponent;
  let fixture: ComponentFixture<DetailMarketValueChartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetailMarketValueChartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetailMarketValueChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
