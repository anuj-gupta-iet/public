import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-mourning-news',
  templateUrl: './mourning-news.component.html',
  styleUrls: ['./mourning-news.component.css']
})
export class MourningNewsComponent implements OnInit {

    constructor(private router: Router) { }

  ngOnInit(): void {
  }

  onEventThumbClick(mourningNewsId: any) {
      console.log(mourningNewsId);
      this.router.navigate(['mourning-news-home-page'],{ queryParams: { mourningnewsid: mourningNewsId} });
    }
}
