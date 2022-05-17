import {Component, OnInit, ViewChildren} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {InputComponent} from "../components/input/input/input.component";
import {ActivatedRoute} from '@angular/router';
import {Router} from '@angular/router'
import Swal from 'sweetalert2'

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

  @ViewChildren(InputComponent) inputs: InputComponent[] = [];

  id: string | null | undefined;

  constructor(private http: HttpClient, private route: ActivatedRoute, private router: Router) {

    this.id = route.snapshot.params['id'];


  }

  ngOnInit(): void {
  }

  response: Observable<string> | undefined

  resetPassword() {
    console.log(this.id)
    let param = Array.from(this.inputs)[0].value
    let confirmation = Array.from(this.inputs)[1].value

    //let regexp = '^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,}$'

    if (param.length < 8) {
      Swal.fire({
        title: 'Password does not meet the requirements',
        icon: 'error',
        timer: 5000,
      })
      return
    }

    if (param == '' || confirmation == '') {
      Swal.fire({
        title: 'Fill all fields',
        icon: 'error',
        timer: 5000,
      })
      return
    }

    if (param != confirmation) {
      Swal.fire({
        title: 'Passwords do not match',
        icon: 'error',
        timer: 5000,
      })
      return
    }


    let body = JSON.stringify({param})
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Accept': 'application/json'})
    }

    this.response = this.http.patch<string>('/user/reset-password/' + this.id, body, httpOptions)
    console.log(this.response)

    this.response.subscribe(res => {
        this.router.navigate(['login'])
        Swal.fire({
          title: 'Your password is changed!',
          icon: 'success',
          timer: 3000
        })

      }, error => {

        if (error.status == 400) {

          Swal.fire({
            title: 'Reset password is not available',
            icon: 'error',
            timer: 5000,
          })
        } else
          console.log(error)
      }
    )

  }

}
