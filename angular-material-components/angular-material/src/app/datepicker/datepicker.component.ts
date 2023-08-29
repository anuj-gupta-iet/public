import { Component, OnInit, ViewChild } from '@angular/core';
import { MatCheckbox } from '@angular/material/checkbox';

@Component({
  selector: 'app-datepicker',
  templateUrl: './datepicker.component.html',
  styleUrls: ['./datepicker.component.css']
})
export class DatepickerComponent implements OnInit {

  @ViewChild("myDatepicker") myDatepicker: MatCheckbox;
  mydate: string;

  constructor() { }

  ngOnInit(): void {
  }

  calculateDate(){
    console.log("hii");
    console.log(this.myDatepicker);
  }
}
