import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeButtonComponent } from './home-button/home-button.component';
import { HeaderComponent } from './header/header.component';
import { SearchBlockComponent } from './search-block/search-block.component';
import { ShareNetworksComponent } from './share-networks/share-networks.component';
import { UserHeaderMenuComponent } from './user-header-menu/user-header-menu.component';
import { UserAndLanguageComponent } from './user-and-language/user-and-language.component';
import { LanguageMenuComponent } from './language-menu/language-menu.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeButtonComponent,
    HeaderComponent,
    SearchBlockComponent,
    ShareNetworksComponent,
    UserHeaderMenuComponent,
    UserAndLanguageComponent,
    LanguageMenuComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
