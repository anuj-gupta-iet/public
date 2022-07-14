import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GodGaneshPrayerComponent } from './god-ganesh-prayer.component';

describe('GodGaneshPrayerComponent', () => {
  let component: GodGaneshPrayerComponent;
  let fixture: ComponentFixture<GodGaneshPrayerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GodGaneshPrayerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GodGaneshPrayerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
