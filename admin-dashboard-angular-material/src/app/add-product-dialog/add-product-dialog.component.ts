import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { BackendApiService } from '../services/backend-api.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { SnackBarUtil } from '../util/SnackBarUtil';

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
  actionBtn:string = "Save";

  constructor(private formBuilder: FormBuilder, 
    private api: BackendApiService,
    private dialogRef: MatDialogRef<AddProductDialogComponent>,
    @Inject(MAT_DIALOG_DATA) private editData: any) { }

  ngOnInit(): void {
    this.productForm = this.formBuilder.group({
      productName : ['', Validators.required],
      productCategory : ['', Validators.required],
      productDate : ['', Validators.required],
      productFreshness : ['', Validators.required],
      productPrice : ['', Validators.required],
      comments : ['', Validators.required]
    });
    console.log(this.editData);
    if(this.editData) {
      this.actionBtn = "Update";
      this.productForm.controls['productName'].setValue(this.editData.productName);
      this.productForm.controls['productCategory'].setValue(this.editData.productCategory);
      this.productForm.controls['productDate'].setValue(this.editData.productDate);
      this.productForm.controls['productFreshness'].setValue(this.editData.productFreshness);
      this.productForm.controls['productPrice'].setValue(this.editData.productPrice);
      this.productForm.controls['comments'].setValue(this.editData.comments);
    }
  }

  addProduct(){
    console.log(this.productForm.value);
    console.log(this.productForm.valid);
    console.log(this.productForm);
    console.log(this.actionBtn=="Save");
    SnackBarUtil.logMsg("Test Message");
    if(this.productForm.valid){
      if(this.actionBtn=="Save") {
        this.api.addProduct(this.productForm.value)
        .subscribe({
          next:(res)=>{
            this.dialogRef.close("Product Added Successfully");
          },
          error:()=>{
            this.dialogRef.close("Error while adding Product");
          }
        });
      } else {
        this.api.updateProduct(this.editData.id, this.productForm.value)
        .subscribe({
          next:(res)=>{
            this.dialogRef.close("Product Updated Successfully");
          },
          error:()=>{
            this.dialogRef.close("Error while updating Product");
          }
        });
      }
    }  
  }

}
