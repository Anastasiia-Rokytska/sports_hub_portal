import {Component, HostListener, OnInit, ElementRef} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {EventEmitterService} from "../event-emitter.service";
import {ActivatedRoute, Router} from "@angular/router";

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
  constructor(private http: HttpClient, private eRef: ElementRef, private eventEmitterService: EventEmitterService, private route: ActivatedRoute, private router: Router) {}

  secondMenuVisible: boolean = false;
  thirdMenuVisible: boolean = false;
  menuItems: MenuItem[] = [];
  secondMenuItems: MenuItem[] = [];
  thirdMenuItems: MenuItem[] = [];
  path_arr: Map<number,string>[] = [];

  ngOnInit(): void {
    this.http.get<MenuItem[]>('/api/category/category').subscribe(data => {
      this.menuItems = data;
    });
  }

  async showSecondMenu(id: number, name: string){
    if(this.router.url != '/' && this.router.url != '/admin/article'){
      this.router.navigate(['/'], {relativeTo: this.route});
      return;
    }
    else {
      this.thirdMenuVisible = false;
      if (id == 0) {
        this.secondMenuVisible = false;
        this.path_arr = [new Map<number, string>().set(id, name)];
        this.eventComponentFunction(this.path_arr)
      } else {
        await this.http.get<MenuItem[]>('/api/category/parent/visible/' + id).subscribe(data => {
          if (data.length > 0) {
            this.secondMenuVisible = true;
            this.secondMenuItems = data;
          } else {
            this.secondMenuVisible = false;
          }
          this.path_arr = [new Map<number, string>().set(id, name)];
          this.eventComponentFunction(this.path_arr)
        });

      }
    }
  }

  async showThirdMenu(id: number, name: string){
    if(id == 13){
      this.path_arr[0].forEach((value,key) =>{
        id = key;
      })
    }
    await this.http.get<MenuItem[]>('/api/category/teams/visible/' + id).subscribe(data => {
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
