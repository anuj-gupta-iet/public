import { Component, OnInit, Inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DOCUMENT } from '@angular/common';
import { SlideshowService } from '../slideshow.service';

@Component({
  selector: 'app-image-gallery',
  templateUrl: './image-gallery.component.html',
  styleUrls: ['./image-gallery.component.css']
})
export class ImageGalleryComponent implements OnInit {

  constructor(private route: ActivatedRoute, @Inject(DOCUMENT) document: any, private slideshowService: SlideshowService) { }
  gallerySequence: any;
  currentIndex = 0;
  visibleGalleryNumber: any;
  ngOnInit(): void {
    this.gallerySequence = this.slideshowService.getGallerySequence();
    this.visibleGalleryNumber = this.gallerySequence[this.currentIndex];
    this.route.queryParams.subscribe(params => {
      var galleryNumber = params['gallerynumber'];
      console.log(galleryNumber)
      console.log(this.gallerySequence);
      for (let i = 0; i < this.gallerySequence.length; i++) {
        if (this.gallerySequence[i] == galleryNumber) {
          console.log("index: " + i);
          this.currentIndex = i;
          this.visibleGalleryNumber = this.gallerySequence[this.currentIndex];
          break;
        }
      }
    });
  }

  nextSlide() {
    document.documentElement.scrollTop = 0;
    if (this.currentIndex == this.gallerySequence.length - 1) {
      this.currentIndex = 0;
    } else {
      this.currentIndex++;
    }
    this.visibleGalleryNumber = this.gallerySequence[this.currentIndex];
  }

  prevSlide() {
    if (this.currentIndex == 0) {
      this.currentIndex = this.gallerySequence.length - 1;
    } else {
      this.currentIndex--;
    }
    this.visibleGalleryNumber = this.gallerySequence[this.currentIndex];
  }

}
