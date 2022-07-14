import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SocietyPrideComponent } from './society-pride.component';

describe('SocietyPrideComponent', () => {
  let component: SocietyPrideComponent;
  let fixture: ComponentFixture<SocietyPrideComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SocietyPrideComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SocietyPrideComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
