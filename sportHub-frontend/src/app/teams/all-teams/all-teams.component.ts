import { AfterViewInit, Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { Team } from 'src/app/classes/team';

const ELEMENT_DATA: any[] = [
  {position: 1, name: 'Hydrogen', weight: 1.0079, symbol: 'H'},
  {position: 2, name: 'Helium', weight: 4.0026, symbol: 'He'},
  {position: 3, name: 'Lithium', weight: 6.941, symbol: 'Li'},
  {position: 4, name: 'Beryllium', weight: 9.0122, symbol: 'Be'},
  {position: 5, name: 'Boron', weight: 10.811, symbol: 'B'},
  {position: 6, name: 'Carbon', weight: 12.0107, symbol: 'C'},
  {position: 7, name: 'Nitrogen', weight: 14.0067, symbol: 'N'},
  {position: 8, name: 'Oxygen', weight: 15.9994, symbol: 'O'},
  {position: 9, name: 'Fluorine', weight: 18.9984, symbol: 'F'},
  {position: 10, name: 'Neon', weight: 20.1797, symbol: 'Ne'},
];
@Component({
  selector: 'app-all-teams',
  templateUrl: './all-teams.component.html',
  styleUrls: ['./all-teams.component.css']
})
export class AllTeamsComponent implements OnInit, AfterViewInit {

  @Input() teams!: Array<Team>

  pagesStructure = {
    pages: new Array(),
    resultsPerPage: [5],
    selectedResultPerPage: 5,
    firstRow: 1,
    lastRow: 5
  }

  // pages: Array<number> = new Array()
  // resultsPerPage = [5]
  // selectedResultPerPage: number = this.resultsPerPage[0]
  classRow = 'activated_row'
  visible = false
  currentPage = 1

  constructor() { }

  displayedColumns: string[] = ['team', 'location', 'addedAt', 'category', 'subcategory'];

  ngOnInit(): void {
  }

  ngAfterViewInit(){
    for (let i = 1; i <= this.teams.length / this.pagesStructure.selectedResultPerPage; i++) { this.pagesStructure.pages.push(i) }
    if ( this.pagesStructure.pages.length == 0) this.pagesStructure.pages.push(1)
    for (let i = 10; i <= this.teams.length; i += 5) { this.pagesStructure.resultsPerPage.push(i) }
    if ( this.pagesStructure.lastRow > this.teams.length ) this.pagesStructure.lastRow = this.teams.length
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

}
