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
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';

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
    ForgotPasswordComponent,
    ResetPasswordComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
