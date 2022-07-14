import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MatrimonyComponent } from './matrimony/matrimony.component';
import { MantrimandalComponent } from './mantrimandal/mantrimandal.component';
import { HomeComponent } from './home/home.component';
import { MahasabhasComponent } from './mahasabhas/mahasabhas.component';
import { MatrimonyViewProfileComponent } from './matrimony-view-profile/matrimony-view-profile.component';
import { EventsHomePageComponent } from './events-home-page/events-home-page.component';
import { SocietyPrideHomePageComponent } from './society-pride-home-page/society-pride-home-page.component';
import { MourningNewsHomePageComponent } from './mourning-news-home-page/mourning-news-home-page.component';
import { EditorialHomePageComponent } from './editorial-home-page/editorial-home-page.component';
import { PresidentialAddressHomePageComponent } from './presidential-address-home-page/presidential-address-home-page.component';
import { AboutDevelopersComponent } from './about-developers/about-developers.component';
import { ImageGalleryComponent } from './image-gallery/image-gallery.component';
import { TestComponent } from './test/test.component';

const routes: Routes = [
      { path: 'home', component: HomeComponent },
      { path: 'matrimony', component: MatrimonyComponent },
      { path: 'matrimony-individual', component: MatrimonyViewProfileComponent },
      { path: 'mantrimandal', component: MantrimandalComponent },
      { path: 'mahasabhas', component: MahasabhasComponent },
      { path: 'events-home-page', component: EventsHomePageComponent },
      { path: 'society-pride-home-page', component: SocietyPrideHomePageComponent },
      { path: 'mourning-news-home-page', component: MourningNewsHomePageComponent },
      { path: 'editorial-home-page', component: EditorialHomePageComponent },
      { path: 'presidential-address-home-page', component: PresidentialAddressHomePageComponent },
      { path: 'image-gallery', component: ImageGalleryComponent },
      { path: 'about-developers', component: AboutDevelopersComponent },
      { path: 'image-gallery', component: ImageGalleryComponent },
      { path: 'test', component: TestComponent },
      { path: '', component: HomeComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
