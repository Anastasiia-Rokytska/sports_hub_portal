import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";
import {EventEmitterService} from "../event-emitter.service";

interface MenuItem {
  id: number;
  hidden: boolean;
  name: string;
  parentId: number;
}

interface Article {
  id: number;
  title: string;
  caption: string;
  author: string;
  categories: MenuItem[];
  content: string;
  publishedDate: string;
  commentable: boolean;
  language: string;
}

@Component({
  selector: 'main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {
  carouselArticles: Article[] = [];
  activeArticle: Article = {
    id: 0,
    title: 'Temp_title',
    caption: 'caption',
    author: 'AUTHOR',
    categories: [],
    content: 'Content',
    publishedDate: '2012-12-12',
    commentable: false,
    language: 'English'
  };
  otherArticles: Article[] = [];
  page: number = 1;
  noArticles: boolean = true;
  relativePath: string = '';

  constructor(private http: HttpClient, private route: ActivatedRoute, private router: Router,
              private eventEmitterService: EventEmitterService) {

  }


  ngOnInit(): void {
    if (this.eventEmitterService.subsVar == undefined) {
      this.eventEmitterService.subsVar = this.eventEmitterService.invokeArticlesRender.subscribe((temp: Map<number,string>[]) => {
        this.refreshArticlesByCategory(temp);
      });
    }
    this.refreshArticlesByCategory([new Map<number,string>().set(0, "Home")]);
  }

  refreshArticles(id: number = 0) {
    this.page = 1;
    this.noArticles = true;
    this.getArticle(id).subscribe((response: any) => {
      console.log("response: ", response);
      if (response.length > 0) {
        this.noArticles = false;
        this.activeArticle = response[0];
        this.carouselArticles = response;
        /*if(response.length > 4){
          this.otherArticles = response.slice(4, response.length);
        }
        console.log("carouselArticles: ", this.carouselArticles);
        console.log("otherArticles: ", this.otherArticles);*/
      }
      else{
        this.noArticles = true;
        this.carouselArticles = [];
        /*this.otherArticles = [];*/
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
      return this.http.get<Article>("/api/article/", httpOptions)
    }
    else{
      return this.http.get<Article>("/api/article/category/" + id, httpOptions)
    }
  }

  showCarouselArticle(id: number) {
    this.activeArticle = this.carouselArticles[id];
    console.log("article: ", this.activeArticle);
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
    this.refreshArticles(id);
  }



  goToArticle(id: number) {
    this.router.navigate(['/article/' + id], {relativeTo: this.route});
  }


}
