import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ButtonComponent } from './button/button.component';
import { Routes, RouterModule } from '@angular/router';
import { TestComponent } from './test/test.component';
import { GridSystemComponent } from './grid-system/grid-system.component';
import { ButtonGroupComponent } from './button-group/button-group.component';
import { FormsModule } from '@angular/forms';

const appRoutes: Routes = [
  { path: 'button', component: ButtonComponent },
  { path: 'grid-system', component: GridSystemComponent },
  { path: 'test', component: TestComponent },
  { path: 'button-group', component: ButtonGroupComponent },
]

@NgModule({
  declarations: [
    AppComponent,
    ButtonComponent,
    TestComponent,
    GridSystemComponent,
    ButtonGroupComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule, // bootstrap,
    FormsModule, //for ngModel 
    RouterModule.forRoot(appRoutes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
