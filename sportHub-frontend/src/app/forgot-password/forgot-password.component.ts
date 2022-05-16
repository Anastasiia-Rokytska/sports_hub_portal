import {Component, OnInit, ViewChildren} from '@angular/core';
import {InputComponent} from "../components/input/input/input.component";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  @ViewChildren(InputComponent) inputs: InputComponent[] = [];


  constructor(private http: HttpClient) {

  }

  ngOnInit(): void {
  }

  errorVisible = "display: none"

  response: Observable<string> | undefined
  hideInput = true
  showCheck = false

  hideMessages() {
    this.errorVisible = "display: none"
  }

  request_link() {

    let param = Array.from(this.inputs)[0].value
    let body = JSON.stringify({param})
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Accept': 'application/json'})
    }

    this.response = this.http.post<string>('/user/forgot-password', body, httpOptions)
    console.log(this.response)
    console.log("hello")

    this.response.subscribe(res => {
        this.hideInput = false
        this.showCheck = true
        console.log(res)
    }, error => {
      console.log(error)
    })

  }


}
