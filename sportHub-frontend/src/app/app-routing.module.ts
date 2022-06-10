import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from "./login/login.component";
import { PersonalPageComponent } from './personal-page/personal-page.component';
import {SignupComponent} from "./signup/signup.component";
import {MainPageComponent} from "./main-page/main-page.component";
import {AdminPanelComponent} from "./admin-panel/admin-panel.component";
import {ForgotPasswordComponent} from "./forgot-password/forgot-password.component";
import {ResetPasswordComponent} from "./reset-password/reset-password.component";
import { TeamsComponent } from './teams/teams/teams.component';

const routes: Routes = [
  {path: '', component: MainPageComponent},
  {path: 'login', component: LoginComponent},
  {path: 'sign-up', component: SignupComponent},
  {path: 'personal_page', component: PersonalPageComponent},
  {path: 'admin', component: AdminPanelComponent},
  {path: 'forgot-password', component: ForgotPasswordComponent},
  {path: 'reset-password/:id', component: ResetPasswordComponent},
  {path: 'teams', component: TeamsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
