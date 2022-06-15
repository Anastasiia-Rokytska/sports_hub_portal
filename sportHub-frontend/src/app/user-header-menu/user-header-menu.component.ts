import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-header-menu',
  templateUrl: './user-header-menu.component.html',
  styleUrls: ['./user-header-menu.component.css']
})
export class UserHeaderMenuComponent implements OnInit {

  constructor() { }
  @Input() userName = "Ivan Baloh"
  @Input() userEmail = "ivanbaloh@gmail.com"
  @Input() photoLink = "assets/images/userPhoto.jpg"
  ngOnInit(): void {
  }

}
