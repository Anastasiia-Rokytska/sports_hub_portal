import { Component, Inject, Input, OnInit } from '@angular/core';

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
  forgotPassword: string = 'display: none'

  @Input() value: string = ''

  constructor() { }

  ngOnInit(): void {
  }
}
