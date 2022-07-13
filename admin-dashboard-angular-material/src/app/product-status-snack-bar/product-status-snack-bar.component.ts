import { Component, OnInit, Inject } from '@angular/core';
import { MAT_SNACK_BAR_DATA } from '@angular/material/snack-bar';

@Component({
  selector: 'app-product-status-snack-bar',
  templateUrl: './product-status-snack-bar.component.html',
  styleUrls: ['./product-status-snack-bar.component.scss']
})
export class ProductStatusSnackBarComponent implements OnInit {

  constructor(@Inject(MAT_SNACK_BAR_DATA) public message: any) { }

  ngOnInit(): void {
    console.log(this.message);
  }

}
