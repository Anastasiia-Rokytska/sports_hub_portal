import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.css']
})
export class TeamsComponent implements OnInit {

  latitude = 51.678418;
  longitude = 7.809007;

  constructor() { }

  ngOnInit(): void {

  }

}
