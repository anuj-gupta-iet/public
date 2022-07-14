import { Component, OnInit, Inject } from '@angular/core';
import { DOCUMENT } from '@angular/common';

@Component({
  selector: 'app-mahasabhas',
  templateUrl: './mahasabhas.component.html',
  styleUrls: ['./mahasabhas.component.css']
})
export class MahasabhasComponent implements OnInit {
  mahasabhaSelected: string = "";
  constructor(@Inject(DOCUMENT) document:any) { }

  ngOnInit(): void {
  }

  onMahasabhaClick(mahasabhaName: string) {
    console.log(mahasabhaName);
    this.mahasabhaSelected = mahasabhaName;
    //document.querySelector('#' + this.mahasabhaSelected)!.scrollIntoView({ behavior: "smooth" });
    console.log("Done");
  }
}
