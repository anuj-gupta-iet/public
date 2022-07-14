import { Component } from '@angular/core';
import { ICellRendererAngularComp } from 'ag-grid-angular';

@Component({
  selector: 'app-matrimony-thumb-image',
  templateUrl: './matrimony-thumb-image.component.html',
  styleUrls: ['./matrimony-thumb-image.component.css']
  /* template: `
  <img src="assets/images/4.jpg"/>
  <button type="button" (click)="onClick($event)">{{label}}</button>
    ` */
})
export class MatrimonyThumbImageComponent implements ICellRendererAngularComp {
  params: any;
  label: string = "Test11";
  srNo: any;
  name: any;
  brideOrGroom: any;
  renderingType: any;

  agInit(params: any): void {
    this.params = params;
    console.log(this.params.data);
    if(this.params.data.gender === 'F') {
      this.brideOrGroom = "bride";
    } else {
      this.brideOrGroom = "groom";
    }
    this.srNo = this.params.data.srNo;
    this.name = this.params.data.name;
    console.log(this.params.renderingType);
    this.renderingType = this.params.renderingType;
    /* this.label = this.params.label || null; */
  }
  refresh(params?: any): boolean {
    return true;
  }

  constructor() {
  }

  onClick($event: any) {
    if (this.params.onClick instanceof Function) {
      // put anything into params u want pass into parents component
      const params = {
        event: $event,
        rowData: this.params.node.data
        // ...something
      }
      this.params.onClick(params);
    }
  }

}
