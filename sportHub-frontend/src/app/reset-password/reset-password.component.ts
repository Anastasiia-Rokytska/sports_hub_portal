import {Component, OnInit, ViewChildren} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {InputComponent} from "../components/input/input/input.component";
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

  @ViewChildren(InputComponent) inputs: InputComponent[] = [];

  id: string | null | undefined;

  constructor(private http: HttpClient, private route: ActivatedRoute) {

    this.id = route.snapshot.params['id'];


  }

  ngOnInit(): void{
  }

  response: Observable<string> | undefined

  resetPassword() {
    console.log(this.id)
    let param = Array.from(this.inputs)[0].value

    let body = JSON.stringify({param})
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Accept': 'application/json'})
    }

    this.response = this.http.patch<string>('/user/reset-password/'+this.id, body, httpOptions)
    console.log(this.response)
    console.log("hello")

    this.response.subscribe(res => {
      console.log(res)
    }, error => {
      console.log(error)
    })

  }

}
