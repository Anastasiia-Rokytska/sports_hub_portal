import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

interface Article{
  id: number,
  title: string,
  content: string,
  photo: string
}
interface Team{
  id: number,
  name: string,
  icon: string
  articles: Array<Article>
}
interface Subscription{
  id: number
}

@Component({
  selector: 'app-team-hub',
  templateUrl: './team-hub.component.html',
  styleUrls: ['./team-hub.component.css']
})
export class TeamHubComponent implements OnInit {

  teams: Array<Team> = new Array()
  teamsCount!: number
  prevPageIcon: string = "assets/pages/previousPage.svg"
  nextPageIcon: string = "assets/pages/nextPage.svg"
  subscriptions: Array<Subscription> = new Array()
  page = 1
  pages: Array<string> = ["01", "02", "03", "04", "05"]

  constructor(
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.loadTeamsInformation()
  }

  loadTeamsInformation(){
    this.loadTeamsCount().subscribe((count) => {
      this.teamsCount = count
      this.calculatePages()
    })
    this.loadTeams(this.page).subscribe((data) => {
      this.teams = data
      this.teams.forEach(team => {
        this.loadArticles(team.id, 1).subscribe((data) => {
          team.articles = data
        })
      })
    })
    this.loadUserSubscriptions().subscribe((data) => {
      this.subscriptions = data
    })
  }

  loadTeams(page: number){
    return this.http.get<Array<Team>>('/team/team-hub/' + page)
  }

  loadArticles(id: number, page: number){
    return this.http.get<Array<Article>>('/api/article/team/' + id + '/' + page)
  }

  loadTeamsCount(){
    return this.http.get<number>('/team/count')
  }

  loadUserSubscriptions(){
    return this.http.get<Array<Subscription>>('/team/subscriptions')
  }

  formatText(text: string):string{
    return text.substring(1, 500) + '...'
  }

  parseInt(number: string){
    return parseInt(number)
  }

  getLength(articles: Array<Article>){
    if (articles == null) return 0
    return articles.length
  }

  changePage(pageNumber: number){
    this.page = pageNumber
    this.calculatePages()
    this.loadTeamsInformation()
  }

  calculatePages(){
    const maxPage = Math.ceil(this.teamsCount / 5)
    this.pages = new Array<string>()
    if (this.page + 4 > maxPage) {
      for (let i = this.page - (this.page + 4 - maxPage); i <= maxPage; i++) {
        if (i < 1) continue
        if (i < 10) {
          this.pages.push("0" + i.toString())
        } else this.pages.push(i.toString())
      }
    } else {
      for (let i = this.page; i <= this.page + 5; i++) {
        if (i < 10) {
          this.pages.push("0" + i.toString())
        } else this.pages.push(i.toString())
      }
    }
  }

  hasSubscription(id: number){
    for (let i = 0; i < this.subscriptions.length; i++) {
      if (this.subscriptions[i].id == id) return true
    }
    return false
  }

  buttonValue(team: Team){
    if (this.hasSubscription(team.id)) return "Unfollow"
    else return "Follow"
  }

  viewArticle(id: number){
    this.router.navigate(['/article/' + id], {relativeTo: this.route});
  }

  subscribe(id: number){
    this.http.get("/team/subscribe/" + id).subscribe(() => {
      this.loadUserSubscriptions().subscribe((response: any) => {
        this.subscriptions = response
      }, (error: any) => {
        console.log(error)
      })
    })
  }

  unsubscribe(id: number){
    this.http.delete("/team/unsubscribe/" + id).subscribe(() => {
      this.loadUserSubscriptions().subscribe((response: any) => {
        this.subscriptions = response
      }, (error: any) => {
        console.log(error)
      })
    })
  }

  subscribeAction(id: number){
    if (this.hasSubscription(id)) this.unsubscribe(id)
    else this.subscribe(id)
  }

}
