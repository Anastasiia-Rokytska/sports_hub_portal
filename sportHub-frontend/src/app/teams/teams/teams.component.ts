import { string } from '@amcharts/amcharts4/core';
import { HttpClient } from '@angular/common/http';
import { Component, InjectionToken, Injector, OnInit, QueryList, Type, ViewChild, ViewChildren } from '@angular/core';
import { Subject } from 'rxjs';
import { Team } from 'src/app/classes/team';
import { DropDownComponent } from 'src/app/components/drop-down/drop-down.component';
import { InputComponent } from 'src/app/components/input/input/input.component';
import { MapComponent } from 'src/app/components/map/map.component';
import { SelectComponent } from 'src/app/components/select/select.component';
import { DynamicService } from 'src/app/dynamic.service';
import Swal from 'sweetalert2';
import { AllTeamsComponent } from '../all-teams/all-teams.component';
import { TeamsHeaderComponent } from '../teams-header/teams-header.component';

export class StringInjector{
  name: string
  constructor(name: string){
    this.name = name
  }
}
@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.css']
})
export class TeamsComponent implements OnInit{

  @ViewChild(SelectComponent) locationField!: SelectComponent
  @ViewChild(InputComponent) teamName!: InputComponent
  @ViewChild(TeamsHeaderComponent) teamsHeader!: TeamsHeaderComponent

  teams = new Array<Team>()
  url!: any
  showLogo = true
  locationsLoaded = false
  locations!: Array<string>

  image!: any

  teamsInjector!: Injector
  categoriesInjector!: Injector
  subcategoriesInjector!: Injector

  mapComponent!: Type<any>
  teamListComponent!: Type<any>
  dropDownCategoryComponent!: Type<any>
  dropDownSubCategoryComponent!: Type<any>

  location: any
  page: number = 1
  count: number = 5
  show = false
  selectedCategory: string = "All"
  selectedSubcategory: string = "All"


  constructor(private http: HttpClient,
              private injector: Injector,
              private dynamicService: DynamicService) {
    dynamicService.data = {
      location: undefined,
      pageSubject: new Subject<number>(),
      countSubject: new Subject<number>(),
      page: this.page,
      count: this.count,
      selectedCategory: new Subject<string>(),
      clearForm: new Subject<boolean>()
    }
    dynamicService.data.pageSubject.subscribe((page: number) => {
      this.page = page
      dynamicService.data.page = page
      this.loadTeams(this.count, this.page)
    })
    dynamicService.data.countSubject.subscribe((count: number) => {
      this.page = Math.ceil(((this.page - 1) * this.count + 1) / count)
      this.count = count
      dynamicService.data.count = count
      dynamicService.data.page = this.page
      this.loadTeams(this.count, this.page)
    })
    dynamicService.data.selectedCategory.subscribe((category: any) => {
      if (category.name == "SELECT CATEGORY"){
        this.selectedCategory = category.value
        if (category.value == "All") this.loadAllSubCategories()
        else this.loadSubCategoriesByCategory(category.value)
      } else if (category.name == "SELECT SUBCATEGORY"){
        this.selectedSubcategory = category.value
      }
    })
  };

  ngOnInit(): void {
    this.loadTeams(this.count, this.page)
    this.loadCategories()
    this.loadAllSubCategories()
    this.loadAllLocations()
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

  clearForm(){
    this.dynamicService.data.clearForm.next(true)
    this.teamName.value = ''
    this.locationField.control.setValue('')
    this.image = this.url = undefined
  }

  addTeam(){
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
    formData.append('selectedCategory', this.selectedCategory)
    formData.append('selectedSubCategory', this.selectedSubcategory)
    this.http.post('/team', formData).subscribe(() => {
      Swal.fire({
        title: "A new team is successfully added!",
        icon: "success",
        timer: 5000,
      })
      this.loadTeams(this.count, this.page)
      this.dynamicService.data.location = this.location = undefined
      this.clearForm()
    }, (error: any) => {
      this.sendError(error.error)
      this.clearForm()
    })
  }

  loadTeams(count: number, page: number){
    this.show = false
    this.http.get('/team/' + count + '/' + page).subscribe((response: any) => {
      let providers = new Array<any>()
      this.teams = new Array<Team>()
      response.forEach((team: any) => {
        let teamIcon, category, subCategory
        if (team.icon == null) teamIcon = null
        else teamIcon = 'data:image/png;base64,' + team.icon
        if (team.category == null) category = "All"
        else category = team.category.name
        if (team.subCategory == null) subCategory = "All"
        else subCategory = team.subCategory.name
        let newTeam = new Team(team.name, team.location, team.addedAt,
          category, subCategory, team.longitude, team.latitude, teamIcon)
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
      this.show = true
    }, () => {
      this.sendError('Teams loading error')
    })
  }

  loadCategories(){
    this.http.get("/api/category/category").subscribe((response: any) => {
      let providers = new Array<any>()
      providers.push({provide: StringInjector, useValue: {name: "SELECT CATEGORY"}, multi: true})
      providers.push({provide: StringInjector, useValue: {name: "All"}, multi: true})
      response.forEach((category: any) => {
        providers.push({provide: StringInjector, useValue: {name: category.name}, multi: true})
      })
      this.categoriesInjector = Injector.create(providers, this.injector)
      console.log(this.categoriesInjector.get(StringInjector))
      this.dropDownCategoryComponent = DropDownComponent
    }, () => {
      this.sendError('Categories loading error')
    })
  }

  loadAllSubCategories(){
    this.http.get("/api/category/subcategory").subscribe((response: any) => {
      let providers = new Array<any>()
      providers.push({provide: StringInjector, useValue: {name: "SELECT SUBCATEGORY"}, multi: true})
      providers.push({provide: StringInjector, useValue: {name: "All"}, multi: true})
      response.forEach((subcategory: any) => {
        providers.push({provide: StringInjector, useValue: {name: subcategory.name}, multi: true})
      })
      this.subcategoriesInjector = Injector.create(providers, this.injector)
      this.dropDownSubCategoryComponent = DropDownComponent
    }, () => {
      this.sendError('Subcategories loading error')
    })
  }

  loadSubCategoriesByCategory(category: string){
    this.http.get('/api/category/subcategory/' + category).subscribe((response: any) => {
      console.log(response)
      let providers = new Array<any>()
      providers.push({provide: StringInjector, useValue: {name: "SELECT SUBCATEGORY"}, multi: true})
      providers.push({provide: StringInjector, useValue: {name: "All"}, multi: true})
      response.forEach((subcategory: any) => {
        providers.push({provide: StringInjector, useValue: {name: subcategory.name}, multi: true})
      })
      this.subcategoriesInjector = Injector.create(providers, this.injector)
      this.dropDownSubCategoryComponent = DropDownComponent
    }, () => {
      this.sendError('Subcategories loading error')
    })
  }

  loadAllLocations(){
    this.http.get("/team/locations").subscribe((response: any) => {
      this.locations = response
      this.locationsLoaded = true
    }, () => {
      this.sendError('Locations loading error')
    })
  }

  showAddingTeam(): boolean {
    if (this.teamsHeader != undefined) return this.teamsHeader.show
    return false
  }

  sendError(message: string){
    Swal.fire({
      title: message,
      icon: "error",
      timer: 5000,
    })
  }
}
