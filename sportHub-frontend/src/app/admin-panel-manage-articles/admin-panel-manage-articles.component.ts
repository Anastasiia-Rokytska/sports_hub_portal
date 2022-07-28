import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Component, OnInit, ViewChildren} from '@angular/core';
import {InputComponent} from "../components/input/input/input.component";
import {EventEmitterService} from "../event-emitter.service";
import Swal from "sweetalert2";
import {Article} from "../classes/Article";
import {ActivatedRoute, Router} from "@angular/router";

interface MenuItem{
  id: number;
  hidden: boolean;
  name: string;
  parentId: number;
}

@Component({
  selector: 'admin-panel-manage-articles',
  templateUrl: './admin-panel-manage-articles.component.html',
  styleUrls: ['./admin-panel-manage-articles.component.css']
})
export class AdminPanelManageArticlesComponent implements OnInit {
  carouselArticles: Article[] = [];
  activeArticle =new Article(0, "", "", "", [], "", "", false,false, "", null);
  otherArticles: Article[] = [];
  page: number = 1;
  noArticles: boolean = true;
  relativePath: string = '';
  categorySorted: boolean = false;
  allArticles: Article[] = [];
  title: string = 'Choose category';
  categories: MenuItem[] = [];
  selectedCategoryId: number = -1;
  selectedSubcategoryId: number = -1;
  selectedTeamId: number = -1;
  subcategories: MenuItem[] = [];
  teams: MenuItem[] = [];
  articleContent: string = '';
  preview: boolean = false;
  previewMode: string = '';
  caption: string = '';
  headline: string = '';
  language: string = 'English';
  publishedDate: string = '';
  userName: string = '';
  userEmail: string = '';
  userId: string = '';
  commentable: boolean = true;
  imageUrl: string | undefined;
  image = '../../../assets/images/userPhoto.jpg';

  constructor(private http: HttpClient, private route: ActivatedRoute, private router: Router,
              private eventEmitterService: EventEmitterService) {

  }

  @ViewChildren(InputComponent) inputs: InputComponent[] = [];

  ngOnInit(): void {
    if (this.eventEmitterService.subsVar == undefined) {
      this.eventEmitterService.subsVar = this.eventEmitterService.invokeArticlesRender.subscribe((temp: Map<number,string>[]) => {
        this.refreshArticlesByCategory(temp);
      });
    }
    this.refreshArticlesByCategory([new Map<number,string>().set(0, "Home")]);
    this.getUser().subscribe((response: any) => {
      console.log("Response: ", response)
      this.userName = response.firstName + ' ' + response.lastName
      this.userId = response.firstName + ' ' + response.lastName
      if (this.userName.length > 19){
        this.userName = this.userName.slice(0, 16) + '...'
      }
      this.userEmail = response.email
    }, (error) => {
      console.log("Error: ", error.error)
    })
    this.http.get<MenuItem[]>('/api/category/parent/visible/0').subscribe(data => {
      this.categories = data;
    });
  }

  refreshArticles(id: number = 0) {
    this.page = 1;
    this.noArticles = true;
    this.getArticle(id).subscribe((response: any) => {
      console.log("response: ", response);
      this.allArticles = new Array<Article>();
      if (response.length > 0) {
        response.forEach((item: Article) => {
          let id = item.id;
          let title = item.title;
          let caption = item.caption;
          let author = item.author;
          let categories = item.categories;
          let content = item.content;
          let publishedDate = item.publishedDate;
          let commentable = item.commentable;
          let language = item.language;
          let icon = item.icon;
          let isPublished = item.isPublished;
          this.allArticles.push(new Article(id, title, caption, author, categories, content, publishedDate, isPublished, commentable, language, icon));
        });
        this.noArticles = false;
        this.activeArticle = this.allArticles[0];
        this.carouselArticles = this.allArticles.slice(0, 4);
        if(this.allArticles.length > 4){
          this.otherArticles = this.allArticles.slice(4, this.allArticles.length);
        }
      }
      else{
        this.noArticles = true;
        this.carouselArticles = [];
        this.otherArticles = [];
      }
    }, (error) => {
      console.log("Error: ", error.error)
    })

  }

  getArticle(id: number = 0) {
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Accept': 'application/json'})
    }
    if(id == 0){
      return this.http.get<Article>("/api/article/manage/", httpOptions)
    }
    else{
      return this.http.get<Article>("/api/article/category/" + id, httpOptions)
    }
  }

  showCarouselArticle(id: number) {
    this.activeArticle = this.carouselArticles[id];
  }

  pageUp() {
    if (this.page + 1 >= this.carouselArticles.length + 1) {
      this.page = 1;
    } else {
      this.page++;
    }
    this.showCarouselArticle(this.page - 1);
  }

  pageDown() {
    if (this.page - 1 == 0) {
      this.page = this.carouselArticles.length;
    } else {
      this.page--;
    }
    this.showCarouselArticle(this.page - 1);
  }

  refreshArticlesByCategory(path: Map<number,string>[]) {
    this.relativePath = '';
    let id: number = 0;
    path.forEach((item) => {
      item.forEach((value, key) => {
        this.relativePath += value + " > ";
        id = key;
      });
    });
    this.categorySorted = id != 0;
    this.refreshArticles(id);
  }



  goToArticle(id: number) {
    this.router.navigate(['/article/' + id], {relativeTo: this.route});
  }

  getUser(){
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Accept': 'application/json'})
    }
    return this.http.get("user/own_information", httpOptions)
  }


  handleSubcategoryChanges(value: number){
    this.selectedSubcategoryId = value;
    this.refreshTeams(this.selectedSubcategoryId);
  }

  async refreshTeams(subcategoryId: number){
    this.selectedTeamId= -1;
    if(this.selectedSubcategoryId != -1) {
      await this.http.get<MenuItem[]>('/api/category/parent/visible/' + subcategoryId).subscribe(data => {
        this.teams = data;
      });
    }
  }

  async refreshSubcategories(categoryId: number){
    this.selectedSubcategoryId = - 1;
    this.selectedTeamId = -1;
    await this.http.get<MenuItem[]>('/api/category/parent/visible/' + categoryId).subscribe(data => {
      this.subcategories = data;
      if(data.length == 0){
        this.teams = [];
      }
    });
  }

  handleCategoryChanges(category: MenuItem){
    this.selectedCategoryId = category.id;
    this.title = category.name;
    if(category.id == 0){
      this.selectedSubcategoryId = -1;
      this.selectedTeamId = -1;
      this.subcategories = [];
      this.teams = [];
    }
    else{
      this.refreshSubcategories(this.selectedCategoryId);
    }
  }

  handleTeamChanges(value: number){
    this.selectedTeamId = value;
  }





  handleLanguageChanges(value: string){
    this.language = value;
  }



}
