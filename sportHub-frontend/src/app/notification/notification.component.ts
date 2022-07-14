import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Stomp } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent implements OnInit {

  icon = "assets/articlesPhoto/photo1.png"
  message = "Posted new article"
  teamName = "Los Angeles Lakers"
  id!: number

  showNotification = false

  socket = new SockJS('/sport-hub-portal')
  stompClient = Stomp.over(this.socket)

  constructor(
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.stompClient.connect({}, (frame: any) => {
      this.stompClient.subscribe("/user/team/new-article",  (message: any) => {
        let article = JSON.parse(message.body)
        console.log(article)
        this.teamName = article.team.name
        this.id = article.id
        this.icon = 'data:image/png;base64,' + article.team.icon
        this.showNotification = true
        setTimeout(() => {this.showNotification = false}, 3500)
      })
    }, (error: any) => {
      console.log(error.message)
    })
  }

  viewArticle(){
    if (this.id != undefined) {
      this.router.navigate(['/article/' + this.id], {relativeTo: this.route});
      this.showNotification = false
    }
  }

  hideNotification(){
    this.showNotification = false
  }
}
