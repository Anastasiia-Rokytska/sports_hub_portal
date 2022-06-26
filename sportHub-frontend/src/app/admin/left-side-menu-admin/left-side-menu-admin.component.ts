import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-left-side-menu-admin',
  templateUrl: './left-side-menu-admin.component.html',
  styleUrls: ['./left-side-menu-admin.component.css']
})
export class LeftSideMenuAdminComponent implements OnInit {

  surveys_image = "assets/icons/adminMenu/Surveys.svg"
  banners_image = "assets/icons/adminMenu/Banners.svg"
  languages_image = "assets/icons/adminMenu/Langueges.svg"
  footer_image = "assets/icons/adminMenu/Footer.svg"
  shares_image = "assets/icons/adminMenu/Shares.svg"
  users_image = "assets/icons/adminMenu/My users.svg"
  ia_image = "assets/icons/adminMenu/IA.svg"
  teams_image = "assets/icons/adminMenu/teams.svg"
  partners_image = "assets/icons/adminMenu/News partnerds.svg"
  last_image = "assets/icons/adminMenu/Group 147.svg"


  constructor(private router: Router) { }

  ngOnInit(): void {
    if (this.router.url.startsWith("/admin/teams")) this.teams_image = "assets/icons/adminMenu/teams_opened.svg"
  }

}
