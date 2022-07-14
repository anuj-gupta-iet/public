import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MourningNewsHomePageComponent } from './mourning-news-home-page.component';

describe('MourningNewsHomePageComponent', () => {
  let component: MourningNewsHomePageComponent;
  let fixture: ComponentFixture<MourningNewsHomePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MourningNewsHomePageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MourningNewsHomePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
