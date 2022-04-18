import { Component, OnInit, ViewChildren } from '@angular/core';
import { InputComponent } from '../components/input/input/input.component';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./../login/login.component.css',
              './signup.component.css']
})
export class SignupComponent implements OnInit {
  constructor(private http: HttpClient) { }

  @ViewChildren(InputComponent) inputs: InputComponent[] = [];

  errorVisible = "display: none"

  ngOnInit(): void {
  }

  response: Observable<string> | undefined

  hideMessages(){
    this.errorVisible = "display: none"
  }

  signup() {
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
