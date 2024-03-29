import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AboutDevelopersComponent } from './about-developers.component';

describe('AboutDevelopersComponent', () => {
  let component: AboutDevelopersComponent;
  let fixture: ComponentFixture<AboutDevelopersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AboutDevelopersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AboutDevelopersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
