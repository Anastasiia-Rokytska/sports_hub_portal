import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-white-small-button',
  templateUrl: './white-small-button.component.html',
  styleUrls: ['./white-small-button.component.css']
})
export class WhiteSmallButtonComponent implements OnInit {

  @Input()
  textButton: string = ''

  constructor() { }

  ngOnInit(): void {
  }

}
