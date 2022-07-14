import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Component, OnInit, ViewChildren} from '@angular/core';
import {InputComponent} from "../components/input/input/input.component";
import Swal from "sweetalert2";

@Component({
  selector: 'app-personal-page',
  templateUrl: './personal-page.component.html',
  styleUrls: ['./personal-page.component.css']
})
export class PersonalPageComponent implements OnInit {

  @ViewChildren(InputComponent) inputs: InputComponent[] = [];

  constructor(
    private http: HttpClient,
  ) {
  }

  firstName: string = ''
  lastName: string = ''
  email: string = ''
  image: string = 'assets/images/userPhoto.jpg'
  teams: Array<any> = []

  public class: Array<string> = ["active_segment", "nonactive_segment", "nonactive_segment", "nonactive_segment"]

  public personal_show = true
  public pass_show = false
  public show_teamHub = false


  ngOnInit(): void {
    this.getUser().subscribe((response: any) => {
      this.firstName = response.firstName
      this.lastName = response.lastName
      this.email = response.email

      if (response.photoLink != null) {
        this.image = response.photoLink
      }
    }, (error) => {
      console.log("Error: ", error.error)
    })
  }


  getUser() {
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Accept': 'application/json'})
    }
    return this.http.get("user/own_information", httpOptions)
  }


  show_personal() {
    this.personal_show = true
    this.pass_show = false
    this.show_teamHub = false

    this.class = ["active_segment", "nonactive_segment", "nonactive_segment", "nonactive_segment"]
  }

  show_pass() {
    this.personal_show = false
    this.pass_show = true
    this.show_teamHub = false

    this.class = ["nonactive_segment", "active_segment", "nonactive_segment", "nonactive_segment"]
  }

  mySurveys() {
  }

  teamHub() {
    this.personal_show = false
    this.pass_show = false
    this.show_teamHub = true
    this.class = ["nonactive_segment", "nonactive_segment", "nonactive_segment", "active_segment"]
    this.teams = []
    this.loadSubscriptions().then((response: any) => {
      console.log(response)
      response.forEach((team: any) => {
        this.teams.push({
          image: 'data:image/png;base64,' + team.icon,
          name: team.name
        })
      });
    })
  }

  loadSubscriptions(){
    return this.http.get('/team/subscriptions').toPromise()
  }


  changePassword() {

    let old_pass = Array.from(this.inputs)[3].value
    let new_pass = Array.from(this.inputs)[4].value
    let confirmation_pass = Array.from(this.inputs)[5].value

    if (old_pass == '' || new_pass == '' || confirmation_pass == '') {
      Swal.fire({
        title: 'Fill all fields',
        icon: 'error',
        timer: 5000,
      })
      return
    }

    this.checkOldPass(old_pass).subscribe(() => {

      if (new_pass.length < 8) {
        Swal.fire({
          title: 'Password does not meet the requirements',
          icon: 'error',
          timer: 5000,
        })
        return
      }

      if (new_pass != confirmation_pass) {
        Swal.fire({
          title: 'Passwords do not match',
          icon: 'error',
          timer: 5000,
        })
        return
      }

      let param = new_pass
      let body = JSON.stringify({param})
      const httpOptions = {
        headers: new HttpHeaders({'Content-Type': 'application/json', 'Accept': 'application/json'})
      }

      let new_response = this.http.patch<string>('/user/change-password', body, httpOptions)
      console.log(new_response)

      new_response.subscribe(() => {


        Swal.fire({
          title: 'Your password is changed!',
          icon: 'success',
          timer: 3000
        })
        Array.from(this.inputs)[3].value = ''
        Array.from(this.inputs)[4].value = ''
        Array.from(this.inputs)[5].value = ''
        Array.from(this.inputs)[3].typeInput = 'password'
        Array.from(this.inputs)[4].value = 'password'
        Array.from(this.inputs)[5].value = 'password'
        this.show_personal()


      }, (error) => {
        console.log("Error: ", error.error)
      })

    }, error => {

      if (error.status == 404) {

        Swal.fire({
          title: 'Error...',
          text: 'Old password is incorrect',
          icon: 'error',
          timer: 5000,
        })
        console.log('User not found')
        return

      }
    })
  }

  checkOldPass(param: string) {
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Accept': 'application/json'})
    }
    let body = JSON.stringify({param})
    return this.http.post<string>("user/check-old-pass", body, httpOptions)
  }


}
