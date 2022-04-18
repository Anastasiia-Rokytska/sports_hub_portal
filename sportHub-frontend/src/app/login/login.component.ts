import { Component, OnInit, ViewChildren } from '@angular/core';
import { InputComponent } from '../components/input/input/input.component';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  constructor(private http: HttpClient) { }

  @ViewChildren(InputComponent) inputs: InputComponent[] = [];

  errorVisible = "display: none"

  constructor() { }

  ngOnInit(): void {
  }

  response: Observable<string> | undefined

  hideMessages(){
    this.errorVisible = "display: none"
  }

  login() {
    let email = Array.from(this.inputs)[0].value
    let password = Array.from(this.inputs)[1].value
    let tokens = ''

    let body = JSON.stringify({email, password})
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Accept': 'application/json'})
    }

    this.response = this.http.post<string>('/user/login', body, httpOptions)
    console.log(this.response)
    this.response.subscribe(res => {
      this.hideMessages()
      console.log(res)
    }, error => {
      this.errorVisible = "display: block"
    })
  }
}
