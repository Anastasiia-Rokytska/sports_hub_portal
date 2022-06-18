import { HttpClient } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Team } from 'src/app/classes/team';
import { DynamicService } from 'src/app/dynamic.service';

@Component({
  selector: 'app-all-teams',
  templateUrl: './all-teams.component.html',
  styleUrls: ['./all-teams.component.css']
})
export class AllTeamsComponent implements OnInit {

  teams: Array<Team> = new Array()

  pagesStructure = {
    pages: new Array(),
    resultsPerPage: new Array(),
  }

  classRow = 'activated_row'
  visible = false

  pagesNumber: number = 1
  teamsCount: number = 0
  selectedResultPerPage: number = 5
  maxResultsPerPage: number = 5
  currentPage: number = 1

  constructor(@Inject(Team) teams: Team[],
              private http: HttpClient,
              private dynamicService: DynamicService) {
    this.teams = teams
  }

  displayedColumns: string[] = ['team', 'location', 'addedAt', 'category', 'subcategory'];

  ngOnInit(): void{
    this.http.get('/team/count').subscribe((response: any) => {
      this.teamsCount = response
      this.pagesNumber = Math.ceil(this.teamsCount! / this.selectedResultPerPage)
      for( let i = 0; i < this.pagesNumber; i++ ) this.pagesStructure.pages.push(i + 1)
      this.maxResultsPerPage = Math.floor(this.teamsCount / 5 * 5)
      for (let i = 5; i <= this.maxResultsPerPage; i += 5) this.pagesStructure.resultsPerPage.push(i)
    }, (error) => {

    })
  }

  changeClass(row: any){
    console.log(row)
    console.log('click')
    if (this.classRow == '') this.classRow = 'activated_row'
    else this.classRow = ''
    console.log('class row = ', this.classRow)
  }

  showButtons(row: any){
    row.selected = !row.selected
    this.visible = !this.visible
    if (this.visible) {
      this.displayedColumns.push('buttons')
    } else {
      this.displayedColumns.pop()
    }
  }

  changePage(target: any){
    this.currentPage = target.innerText
    this.dynamicService.data.page.subscribe((subscriber: any) => {
      subscriber.next(this.currentPage);
    })
    this.dynamicService.data.page.next(this.currentPage)
  }

  getClass(page: any): string | null{
    if (page == this.currentPage) return "currentPage"
    else return null
  }

}
