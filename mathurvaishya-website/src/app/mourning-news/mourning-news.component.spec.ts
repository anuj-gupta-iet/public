import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MourningNewsComponent } from './mourning-news.component';

describe('MourningNewsComponent', () => {
  let component: MourningNewsComponent;
  let fixture: ComponentFixture<MourningNewsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MourningNewsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MourningNewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
