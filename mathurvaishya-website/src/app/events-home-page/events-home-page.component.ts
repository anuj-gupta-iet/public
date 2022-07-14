import { Component, OnInit, Inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DOCUMENT } from '@angular/common'; 

@Component({
  selector: 'app-events-home-page',
  templateUrl: './events-home-page.component.html',
  styleUrls: ['./events-home-page.component.css']
})
export class EventsHomePageComponent implements OnInit {
  eventId : string = "";
  constructor(private route: ActivatedRoute, @Inject(DOCUMENT) document:any) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.eventId = params['eventid'];
      console.log("Event Id Got: "+this.eventId);
    });
  }

  ngAfterViewInit(): void {
    try {
      if(this.eventId === undefined){
        console.log("Undefined");
      } else {
        document.querySelector('#' + this.eventId)!.scrollIntoView({ behavior: "smooth" });
        console.log("Not Undefined");
      }
    } catch (e) { console.log(e); }
    /* try {
      document.querySelector('#' + this.eventId).scrollIntoView();
    } catch (e) { } */
  }
}
