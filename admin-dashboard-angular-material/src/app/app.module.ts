import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { MaterialModule } from './material/material.module';

import { AddProductDialogComponent } from './add-product-dialog/add-product-dialog.component';
import { ProductStatusSnackBarComponent } from './product-status-snack-bar/product-status-snack-bar.component';
import { FormFieldsComponent } from './form-fields/form-fields.component';
import { ProductCrudComponent } from './product-crud/product-crud.component';
import { ParentComponent } from './parent/parent.component';
import { ChildComponent } from './child/child.component';

@NgModule({
  declarations: [
    AppComponent,
    AddProductDialogComponent,
    ProductStatusSnackBarComponent,
    FormFieldsComponent,
    ProductCrudComponent,
    ParentComponent,
    ChildComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    MaterialModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
