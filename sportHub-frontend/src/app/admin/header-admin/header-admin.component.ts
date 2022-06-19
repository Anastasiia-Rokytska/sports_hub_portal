import { last } from '@amcharts/amcharts4/.internal/core/utils/Array';
import { HttpClient } from '@angular/common/http';
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

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.loadAdminInformation()
  }

  loadAdminInformation(){
    this.http.get("/user/own_information").subscribe((response: any) => {
      this.adminName = response.firstName + " " + response.lastName
      this.adminName.slice(1, 18)
    })
  }

}
