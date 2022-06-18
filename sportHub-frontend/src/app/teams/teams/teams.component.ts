import { cos } from '@amcharts/amcharts4/.internal/core/utils/Math';
import { number } from '@amcharts/amcharts4/core';
import { DatePipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { AfterViewInit, ChangeDetectorRef, Component, Injector, OnInit, QueryList, Type, ViewChild, ViewChildren } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Team } from 'src/app/classes/team';
import { DropDownComponent } from 'src/app/components/drop-down/drop-down.component';
import { InputComponent } from 'src/app/components/input/input/input.component';
import { MapComponent } from 'src/app/components/map/map.component';
import { SelectComponent } from 'src/app/components/select/select.component';
import { DynamicService } from 'src/app/dynamic.service';
import { HeaderComponent } from 'src/app/header/header.component';
import Swal from 'sweetalert2';
import { AllTeamsComponent } from '../all-teams/all-teams.component';

@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.css']
})
export class TeamsComponent implements OnInit{

  @ViewChild(SelectComponent) locationField!: SelectComponent
  @ViewChild(InputComponent) teamName!: InputComponent
  @ViewChildren(DropDownComponent) categoriesFields!: Array<DropDownComponent>

  teams = new Array<Team>()
  url!: any
  showChild = false

  showLogo = true
  categories = ["All", "category1", 'category2', 'category3']
  subcategories = ["All", "subcategory1", 'subcategory2', 'subcategory3']

  image!: any

  teamsInjector!: Injector
  mapComponent!: Type<any>
  teamListComponent!: Type<any>
  location: any
  page: number = 1
  count: number = 5

  constructor(private http: HttpClient,
              private injector: Injector,
              private dynamicService: DynamicService) {
    dynamicService.data = {
      location: undefined,
      page: new Subject<number>(),
      count: new Subject<number>()
    }
    dynamicService.data.page.subscribe((page: number) => {
      console.log(page)
      this.page = page
      this.loadTeams(this.count, this.page)
    })
    dynamicService.data.count.subscribe((count: number) => {
      console.log(count)
      this.count = count
      this.loadTeams(this.count, this.page)
    })
  };
  show = false

  ngOnInit(): void {
    this.loadTeams(this.count, this.page)
  }

  getImage() {
    return this.http.get('/assets/LoginPage.png', {responseType: "blob"}).toPromise();
  }

  loadPhoto(event: any){
    let reader = new FileReader()
    this.url = event.target.files[0]
    reader.readAsDataURL(event.target.files[0])
    reader.onload = (readerEvent: any) => {
      this.image = readerEvent.target.result
    }
  }

  updateLocation(){
    if (this.dynamicService.data.location != undefined) this.locationField.control.setValue(this.dynamicService.data.location.location)
    this.location = this.dynamicService.data.location
  }

  addTeam(){
    console.log(this.locationField.control.value)
    if (this.location == undefined){
      this.sendError("Select location on the map!")
      return
    } else if (this.locationField.control.value == '') {
      this.sendError("Enter location name!")
      return
    } else if (this.teamName.value == ''){
      this.sendError("Enter team name!")
      return
    }
    let formData = new FormData()
    formData.append('name', this.teamName.value)
    formData.append('latitude', this.location.latitude)
    formData.append('longitude', this.location.longitude)
    formData.append('location', this.locationField.control.value)
    if (this.url != undefined) formData.append('icon', this.url)
    this.http.post('/team', formData).subscribe(() => {
      Swal.fire({
        title: "A new team is successfully added!",
        icon: "success",
        timer: 5000,
      })
      this.loadTeams(this.dynamicService.data.count, this.dynamicService.data.page)
      this.dynamicService.data.location = this.location = undefined
    }, (error: any) => {
      console.log(error)
      this.sendError(error.error)
    })
  }

  loadTeams(count: number, page: number){
    this.show = false
    this.http.get('/team/' + count + '/' + page).subscribe((response: any) => {
      let providers = new Array<any>()
      this.teams = new Array<Team>()
      response.forEach((team: any) => {
        let teamIcon
        if (team.icon == null) teamIcon = null
        else teamIcon = 'data:image/png;base64,' + team.icon
        let newTeam = new Team(
          team.name,
          team.location,
          team.addedAt,
          "Category1",
          "SubCategory1",
          team.longitude,
          team.latitude,
          teamIcon)
        this.teams.push(newTeam)
        providers.push({
          provide: Team,
          useValue: newTeam,
          multi: true
        })
      })
      this.mapComponent = MapComponent
      this.teamListComponent = AllTeamsComponent
      this.teamsInjector = Injector.create(providers, this.injector)
      console.log(this.teams)
      this.showChild = true
      this.show = true
    }, (error: any) => {
      console.log(error.message)
    })
  }

  sendError(message: string){
    Swal.fire({
      title: message,
      icon: "error",
      timer: 5000,
    })
  }
}
