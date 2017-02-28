import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CardAssetComponent } from './card-asset.component';

describe('CardAssetComponent', () => {
  let component: CardAssetComponent;
  let fixture: ComponentFixture<CardAssetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CardAssetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CardAssetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
