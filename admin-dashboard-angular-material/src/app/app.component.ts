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
    'productFreshness', 'productPrice', 'comments'];
  dataSource!: MatTableDataSource<any>;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private dialog: MatDialog, private _snackBar: MatSnackBar,
    private http: BackendApiService) {
  }

  ngOnInit(): void {
    this.getAllProducts();
  }

  openDialog() {
    const dialogRef = this.dialog.open(AddProductDialogComponent, {
      width: '30%'
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this._snackBar.open(result,'', {
          duration: 3000,//3 sec
          horizontalPosition: 'end',
          verticalPosition: 'top'
        });
        console.log(`Dialog result: ${result}`);
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

  openSnackBar(){
    /* this._snackBar.openFromComponent(ProductStatusSnackBarComponent, {
      duration: 3000,//3 sec
      horizontalPosition: 'end',
      verticalPosition: 'top',
      data: 'Test Data'
    }); */
    
  }
}
