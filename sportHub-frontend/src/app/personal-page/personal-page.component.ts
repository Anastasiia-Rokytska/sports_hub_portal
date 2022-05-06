import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-personal-page',
  templateUrl: './personal-page.component.html',
  styleUrls: ['./personal-page.component.css']
})
export class PersonalPageComponent implements OnInit {

  constructor(
    private http: HttpClient,
  ) { }

  firstName: string = ''
  lastName: string = ''
  email: string = ''
  image: Blob | null = null

  ngOnInit(): void {
    this.getUser().subscribe((response: any) => {
      console.log("Response: ", response)
      this.firstName = response.firstName
      this.lastName = response.lastName
      this.email = response.email
    }, (error) => {
      console.log("Error: ", error.error)
    })
  }

  getUser(){
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Accept': 'application/json'})
    }
    return this.http.get("user/own_information", httpOptions)
  }

  changePassword(){}

  mySurveys(){}

  teamHub(){}
}
