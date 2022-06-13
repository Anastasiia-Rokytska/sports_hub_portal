import { HttpClient } from '@angular/common/http';
import { AfterViewInit, Component, Injector, OnInit, Type, ViewChild } from '@angular/core';
import { Team } from 'src/app/classes/team';
import { MapComponent } from 'src/app/components/map/map.component';
import { SelectComponent } from 'src/app/components/select/select.component';
import { AllTeamsComponent } from '../all-teams/all-teams.component';

@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.css']
})
export class TeamsComponent implements OnInit{

  @ViewChild(MapComponent) map!: MapComponent
  @ViewChild(SelectComponent) locationField!: SelectComponent

  team1 = new Team("Los Angeles Lakers", "Los Angeles, California", "28/05/2019", "NBA", "Western Conference")
  team2 = new Team("Houston Rockets", "Houston, Texas", "22/03/2018", "NBA", "Western Conference")

  teams = new Array<Team>(this.team1, this.team2)
  url!: any

  //   {
  //     name: "Los Angeles Lakers",
  //     location: "Los Angeles, California",
  //     addedAt: "28/05/2019",
  //     category: "NBA",
  //     subCategory: "Western Conference"
  //   },
  //   {
  //     name: "Houston Rockets",
  //     location: "Houston, Texas",
  //     addedAt: "22/03/2018",
  //     category: "NBA",
  //     subCategory: "Western Conference"
  //   },
  //   {
  //     name: "Memphis Grizzlies",
  //     location: "Memphis, Tennessee",
  //     addedAt: "22/03/2018",
  //     category: "NBA",
  //     subCategory: "Western Conference"
  //   },
  //   {
  //     name: "Utah Jazz",
  //     location: "Salt Lake City, Utah",
  //     addedAt: "22/03/2018",
  //     category: "NBA",
  //     subCategory: "Western Conference"
  //   }
  // ]

  showLogo = true
  categories = ["All", "category1", 'category2', 'category3']
  subcategories = ["All", "subcategory1", 'subcategory2', 'subcategory3']

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    this.team1.latitude = 50.8371
    this.team1.longitude = 4.3676

    this.team2.latitude = 40.4167
    this.team2.longitude = -3.7033
  }

  // ngAfterViewInit(){
  //   // console.log('LOCATION', this.map!.locationData)
  // }

  loadPhoto(event: any){
    const formData: FormData = new FormData();
    formData.append('image', event.target.files[0]);
    this.url = event.target.files[0]
    this.http.post(event.target.files[0].name, formData).subscribe(() => {
      console.log('success')
    }, (error) => {
      console.log(error.message)
    })
    let reader = new FileReader()
    reader.readAsDataURL(event.target.files[0])
    reader.onload = (readerEvent: any) => {
      this.url = readerEvent.target.result
    }
  }

  updateLocation(){
    console.log('click')
    console.log('LOCATION', this.map!.locationData)
    this.locationField.value = this.map!.locationData.location
  }
}
