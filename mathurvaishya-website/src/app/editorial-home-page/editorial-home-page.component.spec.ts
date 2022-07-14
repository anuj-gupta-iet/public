import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditorialHomePageComponent } from './editorial-home-page.component';

describe('EditorialHomePageComponent', () => {
  let component: EditorialHomePageComponent;
  let fixture: ComponentFixture<EditorialHomePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditorialHomePageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditorialHomePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
