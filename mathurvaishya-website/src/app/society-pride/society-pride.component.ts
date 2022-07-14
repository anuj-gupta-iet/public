import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-society-pride',
  templateUrl: './society-pride.component.html',
  styleUrls: ['./society-pride.component.css']
})
export class SocietyPrideComponent implements OnInit {

    constructor(private router: Router) { }

  ngOnInit(): void {
  }

  onEventThumbClick(societyPrideId: any) {
      console.log(societyPrideId);
      this.router.navigate(['society-pride-home-page'],{ queryParams: { societyprideid: societyPrideId} });
    }
}
