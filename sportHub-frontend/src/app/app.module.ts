import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http'

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
    LargeButtonComponent,
    LargeButtonComponent
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
