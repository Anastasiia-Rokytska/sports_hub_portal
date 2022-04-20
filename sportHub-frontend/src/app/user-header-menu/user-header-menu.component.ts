import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-header-menu',
  templateUrl: './user-header-menu.component.html',
  styleUrls: ['./user-header-menu.component.css']
})
export class UserHeaderMenuComponent implements OnInit {

  constructor() { }
  userName = "Ivan Baloh"
  userEmail = "ivanbaloh@gmail.com"
  ngOnInit(): void {
  }

}
