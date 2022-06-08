import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-header-admin',
  templateUrl: './header-admin.component.html',
  styleUrls: ['./header-admin.component.css']
})
export class HeaderAdminComponent implements OnInit {

  adminIcon: string = 'assets/icons/userExample.png'
  adminName: string = 'AdminName'
  language: string = 'EN'

  constructor() { }

  ngOnInit(): void {
  }

}
