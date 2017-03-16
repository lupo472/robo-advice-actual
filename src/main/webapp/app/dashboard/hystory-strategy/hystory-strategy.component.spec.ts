import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HystoryStrategyComponent } from './hystory-strategy.component';

describe('HystoryStrategyComponent', () => {
  let component: HystoryStrategyComponent;
  let fixture: ComponentFixture<HystoryStrategyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HystoryStrategyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HystoryStrategyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
