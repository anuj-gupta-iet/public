import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-child',
  templateUrl: './child.component.html',
  styleUrls: ['./child.component.scss']
})
export class ChildComponent implements OnInit {

  @Input() parentToChildProperty = "";
  @Output() childToParentPropertyEvent = new EventEmitter<string>();

  childToParentProperty: string = "";
  constructor() { }

  ngOnInit(): void {
  }

  /* sendToParent(value: string){
    console.log(value);
    this.childToParentPropertyEvent.emit(value);
  } */
  
  sendToParent() {
    console.log(this.childToParentProperty);
    this.childToParentPropertyEvent.emit(this.childToParentProperty);
  }

}
