import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssetGraphComponent } from './asset-graph.component';

describe('AssetGraphComponent', () => {
  let component: AssetGraphComponent;
  let fixture: ComponentFixture<AssetGraphComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssetGraphComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssetGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
