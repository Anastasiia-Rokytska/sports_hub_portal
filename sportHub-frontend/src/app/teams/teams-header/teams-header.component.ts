import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-teams-header',
  templateUrl: './teams-header.component.html',
  styleUrls: ['./teams-header.component.css']
})
export class TeamsHeaderComponent implements OnInit {

  show = false

  constructor() { }

  ngOnInit(): void {
  }

}
