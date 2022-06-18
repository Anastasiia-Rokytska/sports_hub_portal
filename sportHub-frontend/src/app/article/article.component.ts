import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";

interface MenuItem{
  id: number;
  hidden: boolean;
  name: string;
  parentId: number;
}

interface Article{
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
  selector: 'article-component',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.css']
})
export class ArticleComponent implements OnInit {

  id: string | null | undefined;
  article: Article = {id: 0, title: '', caption: '', author: '', categories: [], content: '', publishedDate: '', commentable: false, language: ''};

  constructor(
    private http: HttpClient, private route: ActivatedRoute, private router: Router
  ) {
    this.id = route.snapshot.params['id'];
  }

  ngOnInit(): void {
    this.getArticle().subscribe((response: any) => {
      this.article = response;
      if (response == null){
        this.router.navigate(['/'], {relativeTo: this.route});
      }
    }, (error) => {
      console.log("Error: ", error.error)
    });
  }

  getArticle(){
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Accept': 'application/json'})
    }
    console.log(typeof this.id);
    return this.http.get<Article>("/api/article/" + this.id, httpOptions)
  }

}
