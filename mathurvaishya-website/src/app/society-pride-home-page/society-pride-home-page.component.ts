import { Component, OnInit, Inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DOCUMENT } from '@angular/common';

@Component({
  selector: 'app-society-pride-home-page',
  templateUrl: './society-pride-home-page.component.html',
  styleUrls: ['./society-pride-home-page.component.css']
})
export class SocietyPrideHomePageComponent implements OnInit {
  societyPrideId : string = "";
  constructor(private route: ActivatedRoute, @Inject(DOCUMENT) document:any) { }

  ngOnInit(): void {
      this.route.queryParams.subscribe( params => {
          this.societyPrideId = params['societyprideid'];
          console.log( "societyPrideId Got: " + this.societyPrideId );
      } );
    }

  ngAfterViewInit(): void {
      try {
        if(this.societyPrideId=== undefined){
          console.log("Undefined");
        } else {
          document.querySelector('#' + this.societyPrideId)!.scrollIntoView({ behavior: "smooth" });
          console.log("Not Undefined");
        }
      } catch (e) { console.log(e); }
      /* try {
        document.querySelector('#' + this.eventId).scrollIntoView();
      } catch (e) { } */
    }
}
