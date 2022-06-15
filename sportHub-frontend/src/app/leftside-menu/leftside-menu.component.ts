import {Component, HostListener, OnInit, ElementRef} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {EventEmitterService} from "../event-emitter.service";

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
  constructor(private http: HttpClient, private eRef: ElementRef, private eventEmitterService: EventEmitterService) {}

  secondMenuVisible: boolean = false;
  thirdMenuVisible: boolean = false;
  menuItems: MenuItem[] = [];
  secondMenuItems: MenuItem[] = [];
  thirdMenuItems: MenuItem[] = [];
  path_arr: Map<number,string>[] = [];

  ngOnInit(): void {
    this.http.get<MenuItem[]>('/api/category/parent/visible/0').subscribe(data => {
      this.menuItems = data;
    });
  }

  async showSecondMenu(id: number, name: string){
    this.thirdMenuVisible = false;
    if(id == 0){
      this.secondMenuVisible = false;
      this.path_arr = [new Map<number,string>().set(id, name)];
      this.eventComponentFunction(this.path_arr)
    }else{
      await this.http.get<MenuItem[]>('/api/category/parent/visible/' + id).subscribe(data => {
        if(data.length > 0){
          this.secondMenuVisible = true;
          this.secondMenuItems = data;
        }else{
          this.secondMenuVisible = false;
        }
        this.path_arr = [new Map<number,string>().set(id, name)];
        this.eventComponentFunction(this.path_arr)
      });

    }
  }

  async showThirdMenu(id: number, name: string){
    await this.http.get<MenuItem[]>('/api/category/parent/visible/' + id).subscribe(data => {
      if(data.length > 0){
        this.thirdMenuVisible = true;
        this.thirdMenuItems = data;
      }else{
        this.thirdMenuVisible = false;
        this.clickedOut();
      }
      this.path_arr = [this.path_arr[0], new Map<number,string>().set(id, name)];
      this.eventComponentFunction(this.path_arr)
    });
  }

  handleThirdMenuClick(id: number, name: string){
    this.path_arr = [this.path_arr[0], this.path_arr[1], new Map<number,string>().set(id, name)];
    this.eventComponentFunction(this.path_arr)
    this.clickedOut()
  }

  eventComponentFunction(temp_arr: Map<number,string>[]){
    this.eventEmitterService.RenderArticles(temp_arr);
  }


  clickedOut() {
      this.secondMenuVisible = false;
      this.thirdMenuVisible = false;
  }

}
