import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-personal-page',
  templateUrl: './personal-page.component.html',
  styleUrls: ['./personal-page.component.css']
})
export class PersonalPageComponent implements OnInit {

  constructor(
    private http: HttpClient,
    private route: ActivatedRoute
  ) { }

  firstName: string = ''
  lastName: string = ''
  email: string = ''
  image: Blob | null = null

  ngOnInit(): void {
    let id = this.route.snapshot.params['id'];
    console.log(id)
    this.getUser(id).subscribe((response: any) => {
      this.firstName = response.firstName
      this.lastName = response.lastName
      this.email = response.email
    }, (error) => {
      console.log(error.error)
    })
  }

  getUser(id: number){
    return this.http.get(`user/${id}`)
  }

  changePassword(){}

  mySurveys(){}

  teamHub(){}
}
