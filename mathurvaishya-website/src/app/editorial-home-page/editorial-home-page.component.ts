import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-editorial-home-page',
  templateUrl: './editorial-home-page.component.html',
  styleUrls: ['./editorial-home-page.component.css']
})
export class EditorialHomePageComponent implements OnInit {
  isOlderPostVisible: boolean = false;
  constructor() { }

  ngOnInit(): void {
  }

  onClick(){
    this.isOlderPostVisible = !this.isOlderPostVisible;
  }

}
