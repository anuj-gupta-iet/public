import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SlideshowService {

  constructor() { }

  slidesArray = [
    {"slidenumber":"11","galleryrefnumber":"11","description":"Giving check to Prime Minister's Relief Fund"},
    {"slidenumber":"12","galleryrefnumber":"12","description":"Blood Donation Camp"},
    {"slidenumber":"13","galleryrefnumber":"14","description":"House of Representatives Conference, Ahmedabad"},
    {"slidenumber":"14","galleryrefnumber":"15","description":"Second Ekadashi Udyapan"},
    {"slidenumber":"15","galleryrefnumber":"16","description":"132nd Honor ceremony, Varanasi"},
    {"slidenumber":"16","galleryrefnumber":"17","description":""},
    {"slidenumber":"18","galleryrefnumber":"18","description":"Meritorious Students Honor ceremony"},
  ];

  slideSequence:number[]= [11, 18, 12, 13, 14, 15, 16];
  gallerySequence: number[] = [11, 18, 12, 13, 14, 15, 16, 17];
  getSlides(): any {
    return this.slidesArray;
  }

  getSlideSequence(): number[] {
    return this.slideSequence;
  }

  getGallerySequence(): number[] {
    return this.gallerySequence;
  }
}
