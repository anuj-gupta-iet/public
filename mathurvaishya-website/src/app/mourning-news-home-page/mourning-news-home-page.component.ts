import { Component, OnInit, Inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DOCUMENT } from '@angular/common';

@Component({
  selector: 'app-mourning-news-home-page',
  templateUrl: './mourning-news-home-page.component.html',
  styleUrls: ['./mourning-news-home-page.component.css']
})
export class MourningNewsHomePageComponent implements OnInit {
    mourningNewsId : string = "";
  constructor(private route: ActivatedRoute, @Inject(DOCUMENT) document:any) { }

  ngOnInit(): void {
      this.route.queryParams.subscribe( params => {
          this.mourningNewsId= params['mourningnewsid'];
          console.log( "mourningNewsId Got: " + this.mourningNewsId);
      } );
    }
  
  ngAfterViewInit(): void {
      try {
        if(this.mourningNewsId=== undefined){
          console.log("Undefined");
        } else {
          document.querySelector('#' + this.mourningNewsId)!.scrollIntoView({ behavior: "smooth" });
          console.log("Not Undefined");
        }
      } catch (e) { console.log(e); }
      /* try {
        document.querySelector('#' + this.eventId).scrollIntoView();
      } catch (e) { } */
    }

}
