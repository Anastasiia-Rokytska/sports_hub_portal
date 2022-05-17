import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

interface MenuItem{
  id: number;
  hidden: boolean;
  name: string;
  parentId: number;
}

@Component({
  selector: 'leftside-menu',
  templateUrl: './leftside-menu.component.html',
  styleUrls: ['./leftside-menu.component.css']
})

export class LeftsideMenuComponent implements OnInit {
  constructor(private http: HttpClient) {}

  secondMenuVisible: boolean = false;
  thirdMenuVisible: boolean = false;
  menuItems: MenuItem[] = [];
  secondMenuItems: MenuItem[] = [];
  thirdMenuItems: MenuItem[] = [];

  ngOnInit(): void {
    this.http.get<MenuItem[]>('/api/category/parent/0').subscribe(data => {
      this.menuItems = data;
    });
  }

  showSecondMenu(id: number){
    this.thirdMenuVisible = false;
    if(id == 0){
      this.secondMenuVisible = false;
    }else{
      this.http.get<MenuItem[]>('/api/category/parent/' + id).subscribe(data => {
        if(data.length > 0){
          this.secondMenuVisible = true;
          this.secondMenuItems = data;
        }else{
          this.secondMenuVisible = false;
        }
      });
    }
  }

  showThirdMenu(id: number){
    this.http.get<MenuItem[]>('/api/category/parent/' + id).subscribe(data => {
      if(data.length > 0){
        this.thirdMenuVisible = true;
        this.thirdMenuItems = data;
      }else{
        this.thirdMenuVisible = false;
      }
    });
  }
}
