import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";
import {EventEmitterService} from "../event-emitter.service";
import {Article} from "../classes/Article";

@Component({
  selector: 'main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {
  carouselArticles: Article[] = [];
  activeArticle =new Article(0, "", "", "", [], "", "", false,false, "", null);
  otherArticles: Article[] = [];
  page: number = 1;
  noArticles: boolean = true;
  relativePath: string = '';
  categorySorted: boolean = false;
  allArticles: Article[] = [];

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
          let isPublished = item.isPublished;
          let commentable = item.commentable;
          let language = item.language;
          let icon = item.icon;
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
      return this.http.get<Article>("/api/article/", httpOptions)
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


}
