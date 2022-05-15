import { Component, OnInit, ViewChildren } from '@angular/core';
import { InputComponent } from '../components/input/input/input.component';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  constructor(
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute
    ) { }

  @ViewChildren(InputComponent) inputs: InputComponent[] = [];

  hasError = false
  errorVisible = "display: none"

  ngOnInit(): void {}

  response: Observable<string> | undefined


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
      this.hasError = false
      this.router.navigate(['/personal_page'], { relativeTo: this.route })
    }, error => {
      this.hasError = true
    })
  }
}
