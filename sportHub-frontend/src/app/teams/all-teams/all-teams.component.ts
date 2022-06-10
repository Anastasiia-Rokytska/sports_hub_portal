import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';

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
export class AllTeamsComponent implements OnInit {

  pages = [1, 2, 3, 4, 5]
  count = 50
  resultsPerPage = [5, 10, 15, 20, 25, 30, 35]
  classRow = 'activated_row'
  visible = false

  constructor() { }

  teams = [
    {
      name: 'Team1',
      location: 'Location1',
      addedAt: '01-01-2001',
      category: 'Category1',
      subcategory: 'Subcategory1'
    },
    {
      name: 'Team2',
      location: 'Location2',
      addedAt: '01-01-2002',
      category: 'Category2',
      subcategory: 'Subcategory2'
    },
    {
      name: 'Team2',
      location: 'Location2',
      addedAt: '01-01-2002',
      category: 'Category2',
      subcategory: 'Subcategory2'
    }
  ]

  displayedColumns: string[] = ['position', 'name', 'weight', 'symbol', 'subcategory'];
  dataSource = ELEMENT_DATA;

  ngOnInit(): void {
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
