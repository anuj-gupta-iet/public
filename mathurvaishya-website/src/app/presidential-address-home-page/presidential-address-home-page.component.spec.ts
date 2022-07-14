import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PresidentialAddressHomePageComponent } from './presidential-address-home-page.component';

describe('PresidentialAddressHomePageComponent', () => {
  let component: PresidentialAddressHomePageComponent;
  let fixture: ComponentFixture<PresidentialAddressHomePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PresidentialAddressHomePageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PresidentialAddressHomePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
