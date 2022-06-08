import { Component, OnInit, ViewChildren } from '@angular/core';
import { InputComponent } from '../components/input/input/input.component';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router'
import { Observable } from 'rxjs';


@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./../login/login.component.css',
              './signup.component.css']
})


export class SignupComponent implements OnInit {
  constructor(private http: HttpClient, private router : Router, private route : ActivatedRoute) { }

  @ViewChildren(InputComponent) inputs: InputComponent[] = [];

  errorVisible = "display: none"
  errorEmail = "display: none"
  errorPassword = "display: none"
  verifyAccount = "display: none"

  ngOnInit(): void {
  }

  response: Observable<string> | undefined

  hideMessages(){
    this.errorVisible = "display: none"
    this.errorPassword = "display: none"
    this.errorEmail = "display: none"
    this.verifyAccount = "display: none"
  }


  signup() {
    let firstName = Array.from(this.inputs)[0].value
    let lastName = Array.from(this.inputs)[1].value
    let email = Array.from(this.inputs)[2].value
    let password = Array.from(this.inputs)[3].value

    if(firstName === "" || lastName === "" || email === "" || password === ""){
      this.hideMessages()
      this.errorVisible = "display: block"
      return
    }
    if(password.length < 8){
      this.hideMessages()
      this.errorPassword = "display: block"
      return
    }

    let body = JSON.stringify({firstName, lastName,email, password})
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Accept': 'application/json'})
    }

    this.response = this.http.post<string>('/user/sign-up', body, httpOptions)
    console.log(this.response)
    this.response.subscribe(res => {
      this.hideMessages()
      this.verifyAccount = "display:block"
      console.log(res)
    }, error => {
      if(error.status == 400){
        this.hideMessages()
        this.errorVisible = "display:block"
      }
      else if(error.status == 409){
        this.hideMessages()
        this.errorEmail = "display:block"
      }
      else{
        this.hideMessages()

      }
    })

  }
  goToLogin(){
    this.router.navigate(['/login'], { relativeTo: this.route });
  }
}
