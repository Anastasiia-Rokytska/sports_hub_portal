import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {LoginComponent} from "./login/login.component";
import {SignupComponent} from "./signup/signup.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {

  title = 'sportHub-frontend';
  header : boolean = true;
  menu: boolean = true;

}
