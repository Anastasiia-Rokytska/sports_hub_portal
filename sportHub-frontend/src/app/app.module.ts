import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { WhiteSmallButtonComponent } from './components/buttons/white-small-button/white-small-button.component';
import { InputComponent } from './components/input/input/input.component';
import { LargeButtonComponent } from './components/buttons/large-button/large-button.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    WhiteSmallButtonComponent,
    InputComponent,
    LargeButtonComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
