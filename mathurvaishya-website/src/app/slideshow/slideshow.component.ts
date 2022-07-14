import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SlideshowService } from '../slideshow.service';

@Component({
  selector: 'app-slideshow',
  templateUrl: './slideshow.component.html',
  styleUrls: ['./slideshow.component.css']
})
export class SlideshowComponent implements OnInit {

  constructor(private router: Router, private slideshowService: SlideshowService) { }
  slideSequence: any;
  currentIndex = 0;
  visibleSlideNumber: any;
  slidesArray: any;
  ngOnInit(): void {
    this.slidesArray = this.slideshowService.getSlides();
    this.slideSequence = this.slideshowService.getSlideSequence();
    this.visibleSlideNumber = this.slideSequence[this.currentIndex];
    console.log(this.slidesArray);
    console.log(this.visibleSlideNumber);
  }

  currentSlide(indexOfelement: number) {
    console.log(indexOfelement);
    this.currentIndex = indexOfelement;
    this.visibleSlideNumber = this.slideSequence[this.currentIndex];
  }

  plusSlides() {
    if (this.currentIndex == this.slideSequence.length - 1) {
      this.currentIndex = 0;
    } else {
      this.currentIndex++;
    }
    this.visibleSlideNumber = this.slideSequence[this.currentIndex];
  }

  minusSlides() {
    if (this.currentIndex == 0) {
      this.currentIndex = this.slideSequence.length - 1;
    } else {
      this.currentIndex--;
    }
    this.visibleSlideNumber = this.slideSequence[this.currentIndex];
  }

  onMoreImagesClick() {
    var galleryrefnumber: any;
    for (let i = 0; i < this.slidesArray.length; i++) {
      if (this.slidesArray[i].slidenumber == this.visibleSlideNumber) {
        galleryrefnumber = this.slidesArray[i].galleryrefnumber;
        break;
      }
    }
    this.router.navigate(['image-gallery'], { queryParams: { gallerynumber: galleryrefnumber } });
  }
}
