import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CardAssetClassComponent } from './card-asset-class.component';

describe('CardAssetClassComponent', () => {
  let component: CardAssetClassComponent;
  let fixture: ComponentFixture<CardAssetClassComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CardAssetClassComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CardAssetClassComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
