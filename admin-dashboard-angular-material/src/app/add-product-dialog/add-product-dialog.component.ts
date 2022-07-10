import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { BackendApiService } from '../services/backend-api.service';
import { MatDialogRef } from '@angular/material/dialog';

interface ProductCategory {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-add-product-dialog',
  templateUrl: './add-product-dialog.component.html',
  styleUrls: ['./add-product-dialog.component.scss']
})
export class AddProductDialogComponent implements OnInit {

  categories: ProductCategory[] = [
    {value: 'Fruits', viewValue: 'Fruits'},
    {value: 'Vegetables', viewValue: 'Vegetables'},
    {value: 'Electronics', viewValue: 'Electronics'},
  ];
  freshnessList = ['Brand New', 'Second Hand', 'Refurbished']
  productForm !: FormGroup;

  constructor(private formBuilder: FormBuilder, private api: BackendApiService,
    private dialogRef: MatDialogRef<AddProductDialogComponent>) { }

  ngOnInit(): void {
    this.productForm = this.formBuilder.group({
      productName : ['', Validators.required],
      productCategory : ['', Validators.required],
      productDate : ['', Validators.required],
      productFreshness : ['', Validators.required],
      productPrice : ['', Validators.required],
      comments : ['', Validators.required]
    });
  }

  addProduct(){
    console.log(this.productForm.value);
    console.log(this.productForm.valid);
    if(this.productForm.valid){
      this.api.saveProduct(this.productForm.value)
      .subscribe({
        next:(res)=>{
          this.dialogRef.close("Product Added Successfully");
        },
        error:()=>{
          this.dialogRef.close("Error while adding Product");
        }
      });
    }
  }

}
