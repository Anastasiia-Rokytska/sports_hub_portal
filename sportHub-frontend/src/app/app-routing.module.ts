import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from "./login/login.component";
import { PersonalPageComponent } from './personal-page/personal-page.component';
import {SignupComponent} from "./signup/signup.component";
import {MainPageComponent} from "./main-page/main-page.component";
import {AdminPanelCategoriesComponent} from "./admin-panel-categories/admin-panel-categories.component";
import {ForgotPasswordComponent} from "./forgot-password/forgot-password.component";
import {ResetPasswordComponent} from "./reset-password/reset-password.component";
import { TeamsComponent } from './teams/teams/teams.component';
import {AdminPanelArticlesComponent} from "./admin-panel-articles/admin-panel-articles.component";
import {ArticleComponent} from "./article/article.component";
import {TeamHubComponent} from "./team-hub/team-hub.component";


const routes: Routes = [
  {path: '', component: MainPageComponent},
  {path: 'login', component: LoginComponent},
  {path: 'sign-up', component: SignupComponent},
  {path: 'personal_page', component: PersonalPageComponent},
  {path: 'admin/categories', component: AdminPanelCategoriesComponent},
  {path: 'forgot-password', component: ForgotPasswordComponent},
  {path: 'admin/teams', component: TeamsComponent},
  {path: 'reset-password/:id', component: ResetPasswordComponent},
  {path: 'admin/article', component: AdminPanelArticlesComponent},
  {path: 'article/:id', component: ArticleComponent},
  {path: 'team-hub', component: TeamHubComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
