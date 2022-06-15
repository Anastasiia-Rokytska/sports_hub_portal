import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.css']
})
export class InputComponent implements OnInit {

  but = document.getElementById('password_eye')

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

  isPassword: boolean = false

  constructor() {
  }


  ngOnInit(): void {
    console.log(this.typeInput)
    if (this.typeInput == 'password') this.isPassword = true
    console.log(this.isPassword)
  }


  show_pass() {
    this.typeInput = (this.typeInput == 'password' ? 'text' : 'password')
  }


}
