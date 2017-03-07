import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StrategyClassesGraphComponent } from './strategy-classes-graph.component';

describe('StrategyClassesGraphComponent', () => {
  let component: StrategyClassesGraphComponent;
  let fixture: ComponentFixture<StrategyClassesGraphComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StrategyClassesGraphComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StrategyClassesGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
