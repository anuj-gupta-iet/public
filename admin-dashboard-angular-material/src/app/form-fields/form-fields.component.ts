import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {FormControl} from '@angular/forms';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import { BackendApiService } from '../services/backend-api.service';

@Component({
  selector: 'app-form-fields',
  templateUrl: './form-fields.component.html',
  styleUrls: ['./form-fields.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class FormFieldsComponent implements OnInit {
  textBox:string="";
  textBoxWithClear:string="";
  customTextBox:string = "";
  selectBoxValues: string[] = ["Steak", "Pizza", "Tacos"];
  selectBoxSelectedValue="";

  selectBoxWithAutocompleteControl = new FormControl('');
  selectBoxWithAutocompleteValues: string[] = ["India", "UK", "United States", "Germany", "France", "Italy"];
  filteredOptions: Observable<string[]>;

  singleSelectToggleBtn:string;
  multiSelectToggleBtnControl = new FormControl('');

  constructor(private http: BackendApiService) { }

  ngOnInit(): void {
    //this.getCountries();
    this.filteredOptions = this.selectBoxWithAutocompleteControl.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value || '')),
    );
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.selectBoxWithAutocompleteValues.filter(option => option.toLowerCase().includes(filterValue));
  }

  getCountries(){
    this.http.getCountries()
    .subscribe({
      next:(res)=>{
        console.log(res);
        this.selectBoxWithAutocompleteValues = res;
      },
      error:()=>{
        console.log("Error while Getting Countries");
      }
    });
  }
}
