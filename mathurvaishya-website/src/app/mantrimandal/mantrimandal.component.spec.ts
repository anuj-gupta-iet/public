import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MantrimandalComponent } from './mantrimandal.component';

describe('MantrimandalComponent', () => {
  let component: MantrimandalComponent;
  let fixture: ComponentFixture<MantrimandalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MantrimandalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MantrimandalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
