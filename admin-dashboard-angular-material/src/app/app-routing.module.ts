import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProductCrudComponent } from './product-crud/product-crud.component';
import { FormFieldsComponent } from './form-fields/form-fields.component';
import { ParentComponent } from './parent/parent.component';

const routes: Routes = [
  { path: 'product-crud', component: ProductCrudComponent},
  { path: 'form-fields', component: FormFieldsComponent},
  { path: 'parent-child', component: ParentComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
