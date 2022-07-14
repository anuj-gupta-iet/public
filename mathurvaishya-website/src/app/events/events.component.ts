import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.css']
})
export class EventsComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  onEventThumbClick(eventId: any) {
    console.log(eventId);
    this.router.navigate(['events-home-page'],{ queryParams: { eventid: eventId} });
  }
}
