import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StrategyGraphComponent } from './strategy-graph.component';

describe('StrategyGraphComponent', () => {
  let component: StrategyGraphComponent;
  let fixture: ComponentFixture<StrategyGraphComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StrategyGraphComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StrategyGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
