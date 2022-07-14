import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { SlideshowComponent } from './slideshow/slideshow.component';
import { TestComponent } from './test/test.component';
import { FooterComponent } from './footer/footer.component';
import { MatrimonyComponent } from './matrimony/matrimony.component';
import { RouterModule } from '@angular/router';
import { MantrimandalComponent } from './mantrimandal/mantrimandal.component';
import { HomeComponent } from './home/home.component';
import { AgGridModule } from 'ag-grid-angular';
import { MatrimonyThumbImageComponent } from './matrimony-thumb-image/matrimony-thumb-image.component';
import { ButtonRendererComponent } from './renderer/button-renderer.component';
import { MahasabhasComponent } from './mahasabhas/mahasabhas.component';
import { EditorialComponent } from './editorial/editorial.component';
import { GodGaneshPrayerComponent } from './god-ganesh-prayer/god-ganesh-prayer.component';
import { EventsComponent } from './events/events.component';
import { SocietyPrideComponent } from './society-pride/society-pride.component';
import { MourningNewsComponent } from './mourning-news/mourning-news.component';
import { MatrimonyViewProfileComponent } from './matrimony-view-profile/matrimony-view-profile.component';
import { EventsHomePageComponent } from './events-home-page/events-home-page.component';
import { SocietyPrideHomePageComponent } from './society-pride-home-page/society-pride-home-page.component';
import { MourningNewsHomePageComponent } from './mourning-news-home-page/mourning-news-home-page.component';
import { EditorialHomePageComponent } from './editorial-home-page/editorial-home-page.component';
import { PresidentialAddressComponent } from './presidential-address/presidential-address.component';
import { PresidentialAddressHomePageComponent } from './presidential-address-home-page/presidential-address-home-page.component';
import { AboutDevelopersComponent } from './about-developers/about-developers.component';
import { ImageGalleryComponent } from './image-gallery/image-gallery.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SlideshowComponent,
    TestComponent,
    FooterComponent,
    MatrimonyComponent,
    MantrimandalComponent,
    HomeComponent,
    MatrimonyThumbImageComponent,
    ButtonRendererComponent,
    MahasabhasComponent,
    EditorialComponent,
    GodGaneshPrayerComponent,
    EventsComponent,
    SocietyPrideComponent,
    MourningNewsComponent,
    MatrimonyViewProfileComponent,
    EventsHomePageComponent,
    SocietyPrideHomePageComponent,
    MourningNewsHomePageComponent,
    EditorialHomePageComponent,
    PresidentialAddressComponent,
    PresidentialAddressHomePageComponent,
    AboutDevelopersComponent,
    ImageGalleryComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AgGridModule.withComponents([ButtonRendererComponent, MatrimonyThumbImageComponent])
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
