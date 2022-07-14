import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MahasabhasComponent } from './mahasabhas.component';

describe('MahasabhasComponent', () => {
  let component: MahasabhasComponent;
  let fixture: ComponentFixture<MahasabhasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MahasabhasComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MahasabhasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
