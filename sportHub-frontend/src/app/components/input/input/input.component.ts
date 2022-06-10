import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.css']
})
export class InputComponent implements OnInit {

  @Input()
  typeInput: string = "text"

  @Input()
  placeholder: string = ''

  @Input()
  nameField: string = ''

  @Input()
  id: string = ''

  @Input()
  forgotPassword: string = 'display: none'

  @Input() value: string = ''

  @Input() width: string = '390'

  @Input() height: string = '42'

  style: string = ''

  constructor() {
  }

  ngOnInit(): void {
    this.style = `width: ${this.width}px; height: ${this.height}px;`
  }
}
