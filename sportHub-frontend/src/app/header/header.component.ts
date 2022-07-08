import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  userName = "Ivan Baloh"
  userEmail = "ivanbaloh@gmail.com"
  photoLink = "assets/images/userPhoto.jpg"
  constructor(
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.getUser().subscribe((response: any) => {
      console.log("Response: ", response)
      this.userName = response.firstName + ' ' + response.lastName
      if (this.userName.length > 19){
        this.userName = this.userName.slice(0, 16) + '...'
      }
      this.userEmail = response.email

      if(response.photoLink != null){
        this.photoLink = response.photoLink
      }

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

}
