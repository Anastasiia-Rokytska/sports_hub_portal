import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-drop-down',
  templateUrl: './drop-down.component.html',
  styleUrls: ['./drop-down.component.css']
})
export class DropDownComponent implements OnInit {

  @Input() options: Array<string> = new Array()
  @Input() nameField: string = 'Field'

  constructor() { }

  ngOnInit(): void {
  }

}
