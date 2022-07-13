import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import { AddProductDialogComponent } from './add-product-dialog/add-product-dialog.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ProductStatusSnackBarComponent } from './product-status-snack-bar/product-status-snack-bar.component';
import { BackendApiService } from './services/backend-api.service';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  title = 'admin-dashboard-angular-material';
  displayedColumns: string[] = ['id', 'productName', 'productCategory', 'productDate',
    'productFreshness', 'productPrice', 'comments', 'action'];
  dataSource!: MatTableDataSource<any>;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private dialog: MatDialog, private _snackBar: MatSnackBar,
    private http: BackendApiService) {
  }

  ngOnInit(): void {
    this.getAllProducts();
  }

  addProductDialog() {
    const dialogRef = this.dialog.open(AddProductDialogComponent, {
      width: '30%'
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.openSnackBar(result);
        /*this._snackBar.open(result,'', {
          duration: 3000,//3 sec
          horizontalPosition: 'end',
          verticalPosition: 'top'
        });*/
        this.getAllProducts();
        console.log(`Dialog result: ${result}`);
      }
    });
  }

  editProductDialog(row: any) {
    const dialogRef = this.dialog.open(AddProductDialogComponent, {
      width: '30%',
      data: row
    });
    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.openSnackBar(result);
        this.getAllProducts();
      }
    });
  }
  
  deleteProduct(id:number) {
    this.http.deleteProduct(id)
    .subscribe({
      next:(res)=>{
        console.log(res);
        this.openSnackBar("Product Deleted Successfully");
        this.getAllProducts();
      },
      error:()=>{
        this.openSnackBar("Error while deleting Product");
        console.log("Error while deleting Product");
      }
    });
  }

  getAllProducts(){
    this.http.getAllProduct()
    .subscribe({
      next:(res)=>{
        console.log(res);
        this.dataSource = new MatTableDataSource(res);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
      error:()=>{
        console.log("Error while Getting All Products");
      }
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  openSnackBar(message: any){
    this._snackBar.openFromComponent(ProductStatusSnackBarComponent, {
      duration: 3000,//3 sec
      horizontalPosition: 'end',
      verticalPosition: 'top',
      data: message
    });
    
  }
}
