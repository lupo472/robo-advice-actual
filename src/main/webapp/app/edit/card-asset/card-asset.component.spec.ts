import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By }              from '@angular/platform-browser';
import { DebugElement }    from '@angular/core';
import { CardAssetComponent } from './card-asset.component';

describe('CardAssetComponent', () => {
  let component: CardAssetComponent;
  let fixture: ComponentFixture<CardAssetComponent>;
  let de:      DebugElement;
  let el:      HTMLElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CardAssetComponent ]// declare the test component
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CardAssetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    de = fixture.debugElement.query(By.css('h1')); //Il queryAll metodo restituisce un array di tutto DebugElements che soddisfano il predicato.
    el = de.nativeElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
