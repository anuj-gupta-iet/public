import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-button-group',
  templateUrl: './button-group.component.html',
  styleUrls: ['./button-group.component.css']
})
export class ButtonGroupComponent implements OnInit {

  btn_name: string;
  active_btn_name: string;
  active_outlined_btn_name: string;

  chk_box_1: boolean;
  chk_box_2: boolean;
  chk_box_3: boolean;

  radio_box_val: string;
  constructor() { }

  ngOnInit(): void {
  }

  btnClicked(name: string) {
    this.btn_name = name;
  }
  btnClickedActive(name: string) {
    this.active_btn_name = name;
  }
  btnClickedActiveOutlined(name: string) {
    if (this.active_outlined_btn_name == name) {
      this.active_outlined_btn_name = "";
    } else {
      this.active_outlined_btn_name = name;
    }
  }
}
