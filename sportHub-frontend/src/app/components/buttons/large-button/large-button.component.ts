import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-large-button',
  templateUrl: './large-button.component.html',
  styleUrls: ['./large-button.component.css']
})
export class LargeButtonComponent implements OnInit {

  @Input()
  typeButton: string = 'submit'

  @Input()
  textButton: string = ''

  constructor() { }

  ngOnInit(): void {
  }

}
