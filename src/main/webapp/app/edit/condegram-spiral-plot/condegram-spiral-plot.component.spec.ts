import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CondegramSpiralPlotComponent } from './condegram-spiral-plot.component';

describe('CondegramSpiralPlotComponent', () => {
  let component: CondegramSpiralPlotComponent;
  let fixture: ComponentFixture<CondegramSpiralPlotComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CondegramSpiralPlotComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CondegramSpiralPlotComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
