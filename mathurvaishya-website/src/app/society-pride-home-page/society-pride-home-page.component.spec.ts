import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SocietyPrideHomePageComponent } from './society-pride-home-page.component';

describe('SocietyPrideHomePageComponent', () => {
  let component: SocietyPrideHomePageComponent;
  let fixture: ComponentFixture<SocietyPrideHomePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SocietyPrideHomePageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SocietyPrideHomePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
