import { Component, HostListener } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'mathurvaishya-website';
  
  screenWidth: number = 0;
  
  ngOnInit() {
      console.log(window.innerWidth);
      this.screenWidth = window.innerWidth;
  }
  
  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    console.log(window.innerWidth);
    this.screenWidth = window.innerWidth;
  }
  
  onActivate(event: any) {
    window.scroll(0,0);
  }

}
