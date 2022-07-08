import {Component, OnInit, ViewChildren} from '@angular/core';
import {InputComponent} from "../components/input/input/input.component";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import Swal from "sweetalert2";

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

    if (param == '') {
      Swal.fire({
        title: 'Input the text',
        icon: 'error',
        timer: 5000,
      })
    }

    let body = JSON.stringify({param})
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Accept': 'application/json'})
    }

    this.response = this.http.post<string>('/user/forgot-password', body, httpOptions)
    console.log(this.response)


    this.response.subscribe(res => {
      this.hideInput = false
      this.showCheck = true
    }, error => {

      if (error.status == 400) {

        Swal.fire({
          title: 'Error...',
          text: 'No user with this email is registered\'',
          icon: 'error',
          timer: 5000,
        })
        console.log('User not found')
        return

      }

      if (error.status == 451) {
        Swal.fire({
          title: 'Check your email!',
          text: 'You have already requested for reset password!',
          icon: 'warning',
          timer: 3000
        })
        console.log('User has already requested for reset pass')
        return;

      }
      if(error.status == 503){
        Swal.fire({
          title: 'Error...',
          text: 'Unfortunately, this function is currently unavailable, please try again later\'',
          icon: 'error',
          timer: 5000,
        })
      }
      console.log(error)
    })

  }


}
