import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDatepickerModule } from '@angular/material/datepicker';
/* after adding a component do changes here */

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MaterialButtonComponent } from './material-button/material-button.component';
import { Routes, RouterModule } from '@angular/router';
import { ButtonComponent } from './button/button.component';
import { ButtonToggleComponent } from './button-toggle/button-toggle.component';
import { IconsComponent } from './icons/icons.component';
import { SidenavComponent } from './sidenav/sidenav.component';
import { CheckboxesComponent } from './checkboxes/checkboxes.component';
import { DatepickerComponent } from './datepicker/datepicker.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatNativeDateModule } from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';

const appRoutes: Routes = [
  { path: 'button', component: ButtonComponent },
  { path: 'button-toggle', component: ButtonToggleComponent },
  { path: 'icons', component: IconsComponent },
  { path: 'sidenav', component: SidenavComponent },
  { path: 'checkboxes', component: CheckboxesComponent },
  { path: 'datepicker', component: DatepickerComponent },
]

/* add new angular material modules here */
/* after adding a component do changes here */
const AngularMaterialModules = [
  MatButtonModule,
  MatDividerModule,
  MatButtonToggleModule,
  MatIconModule,
  MatSidenavModule,
  MatCheckboxModule,
  MatDatepickerModule,
  MatFormFieldModule,
  MatNativeDateModule,
  MatInputModule,
  FormsModule
]

@NgModule({
  declarations: [
    AppComponent,
    MaterialButtonComponent,
    ButtonComponent,
    ButtonToggleComponent,
    IconsComponent,
    SidenavComponent,
    CheckboxesComponent,
    DatepickerComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    AngularMaterialModules,
    RouterModule.forRoot(appRoutes)
  ],
  exports: [
    AngularMaterialModules
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
