import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MatrimonyThumbImageComponent } from './matrimony-thumb-image.component';

describe('MatrimonyThumbImageComponent', () => {
  let component: MatrimonyThumbImageComponent;
  let fixture: ComponentFixture<MatrimonyThumbImageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MatrimonyThumbImageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MatrimonyThumbImageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
