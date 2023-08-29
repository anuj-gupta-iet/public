import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { MatCheckbox } from '@angular/material/checkbox';

@Component({
  selector: 'app-checkboxes',
  templateUrl: './checkboxes.component.html',
  styleUrls: ['./checkboxes.component.css']
})
export class CheckboxesComponent implements OnInit {

  @ViewChild("subtask1") subTask1: MatCheckbox;
  @ViewChild("subtask2") subTask2: MatCheckbox;
  @ViewChild("subtask3") subTask3: MatCheckbox;
  @ViewChild("overalltaskstatus") overallTaskStatus: MatCheckbox;

  constructor() { }

  ngOnInit(): void {
    console.log("Hello");
    
  }

  setOverallStatus() {
    console.log(this.subTask1.checked);
    console.log(this.subTask2.checked);
    console.log(this.subTask3.checked);
    console.log(this.overallTaskStatus.checked);
    if (this.subTask1.checked && this.subTask2.checked && this.subTask3.checked) {
      this.overallTaskStatus.indeterminate = false;
      this.overallTaskStatus.checked = true;
    } else {
      this.overallTaskStatus.indeterminate = true;
    }
    /* console.log(st1);
    console.log(st2);
    console.log(st3);
    console.log(ot); */

  }
}
