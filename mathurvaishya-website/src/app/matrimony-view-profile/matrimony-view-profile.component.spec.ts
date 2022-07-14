import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MatrimonyViewProfileComponent } from './matrimony-view-profile.component';

describe('MatrimonyViewProfileComponent', () => {
  let component: MatrimonyViewProfileComponent;
  let fixture: ComponentFixture<MatrimonyViewProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MatrimonyViewProfileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MatrimonyViewProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
