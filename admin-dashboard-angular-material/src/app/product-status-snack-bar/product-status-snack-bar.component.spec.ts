import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductStatusSnackBarComponent } from './product-status-snack-bar.component';

describe('ProductStatusSnackBarComponent', () => {
  let component: ProductStatusSnackBarComponent;
  let fixture: ComponentFixture<ProductStatusSnackBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductStatusSnackBarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductStatusSnackBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
