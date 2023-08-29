import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.css']
})
export class ButtonComponent implements OnInit {

  name: string;

  constructor() { }

  ngOnInit(): void {
  }

  btnClicked(name: string) {
    this.name = name;
  }
}
