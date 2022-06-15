import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { HomeButtonComponent } from './home-button/home-button.component';
import { HeaderComponent } from './header/header.component';
import { SearchBlockComponent } from './search-block/search-block.component';
import { ShareNetworksComponent } from './share-networks/share-networks.component';
import { UserHeaderMenuComponent } from './user-header-menu/user-header-menu.component';
import { UserAndLanguageComponent } from './user-and-language/user-and-language.component';
import { LanguageMenuComponent } from './language-menu/language-menu.component';
import { LoginComponent } from './login/login.component';
import { WhiteSmallButtonComponent } from './components/buttons/white-small-button/white-small-button.component';
import { InputComponent } from './components/input/input/input.component';
import { LargeButtonComponent } from './components/buttons/large-button/large-button.component';
import {SignupComponent} from "./signup/signup.component";
import {LeftsideMenuComponent} from "./leftside-menu/leftside-menu.component";
import { PersonalPageComponent } from './personal-page/personal-page.component';
import { SegmentComponent } from './components/segment/segment/segment.component';
import { MainPageComponent } from './main-page/main-page.component';
import { AdminPanelComponent} from "./admin-panel/admin-panel.component";
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import {AdminPanelArticlesComponent} from "./admin-panel-articles/admin-panel-articles.component";
import {ClickOutsideModule} from "ng-click-outside";
import {ArticleComponent} from "./article/article.component";
import {EventEmitterService} from "./event-emitter.service";

@NgModule({
  declarations: [
    AppComponent,
    HomeButtonComponent,
    HeaderComponent,
    SearchBlockComponent,
    ShareNetworksComponent,
    UserHeaderMenuComponent,
    UserAndLanguageComponent,
    LanguageMenuComponent,
    LoginComponent,
    SignupComponent,
    WhiteSmallButtonComponent,
    InputComponent,
    LargeButtonComponent,
    LargeButtonComponent,
    LeftsideMenuComponent,
    PersonalPageComponent,
    SegmentComponent,
    MainPageComponent,
    AdminPanelComponent,
    LeftsideMenuComponent,
    ForgotPasswordComponent,
    ResetPasswordComponent,
    AdminPanelArticlesComponent,
    ArticleComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ClickOutsideModule
  ],
  providers: [EventEmitterService],
  bootstrap: [AppComponent]
})
export class AppModule { }
