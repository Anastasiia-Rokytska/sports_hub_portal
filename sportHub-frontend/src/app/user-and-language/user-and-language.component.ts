import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-and-language',
  templateUrl: './user-and-language.component.html',
  styleUrls: ['./user-and-language.component.css']
})
export class UserAndLanguageComponent implements OnInit {

  constructor() { }

  @Input() userName = "Ivan Baloh"
  @Input() userEmail = "ivanbaloh@gmail.com"

  ngOnInit(): void {
  }

}
