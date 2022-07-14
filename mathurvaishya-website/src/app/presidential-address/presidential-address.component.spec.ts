import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PresidentialAddressComponent } from './presidential-address.component';

describe('PresidentialAddressComponent', () => {
  let component: PresidentialAddressComponent;
  let fixture: ComponentFixture<PresidentialAddressComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PresidentialAddressComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PresidentialAddressComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
