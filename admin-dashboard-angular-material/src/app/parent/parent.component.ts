import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-parent',
  templateUrl: './parent.component.html',
  styleUrls: ['./parent.component.scss']
})
export class ParentComponent implements OnInit {

  parentToChildProperty:string = "";
  childToParentProperty:string = "";
  constructor() { }

  ngOnInit(): void {
  }

  receiveFromChild(value: string){
    this.childToParentProperty = value;
  }

}
