import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Comment} from "../classes/Comment";
import {Like} from "../classes/Like";
import {User} from "../classes/User";
import Swal from "sweetalert2";
import {Article} from "../classes/Article";

@Component({
  selector: 'article-component',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.css']
})
export class ArticleComponent implements OnInit {
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json', 'Accept': 'application/json'})
  }
  id: string | null | undefined;
  article = new Article(0, "","","",[],"","",false,"",null)
  userId: number = 0;
  comments: Comment[] = [];
  noComments: boolean = false;
  repliedComment: Comment = new Comment(0, "", [], new User(0, "", "", ""), [], "", false);
  showRepliedMessages: boolean = false;
  userPhoto: string = "";
  commentContent: string = "";
  reply: string = "";
  edit: boolean = false;
  editCommentContent: Comment = new Comment(0, "", [], new User(0, "", "", ""), [], "", false);


  hideReplyTextArea: Boolean[] = [];

  constructor(
    private http: HttpClient, private route: ActivatedRoute, private router: Router
  ) {
    this.id = route.snapshot.params['id'];
  }

  ngOnInit(): void {
    this.getArticle().subscribe((response: any) => {
      if (response == null) {
        this.router.navigate(['/'], {relativeTo: this.route});
      }
      this.article = new Article(response.id, response.title, response.caption, response.author, response.categories, response.content, response.publishedDate, response.commentable, response.language, response.icon);
    }, (error) => {
      console.log("Error: ", error.error)
    });
    this.reloadComments()
    this.http.get("user/own_information", this.httpOptions).subscribe((response: any) => {
      this.userId = response.id

      if (response.photoLink != null) {
        this.userPhoto = response.photoLink;
      } else {
        this.userPhoto = "../../assets/images/userPhoto.jpg";
      }
    }, (error) => {
      console.log("Error: ", error.error)
    });
  }

  getArticle() {
    return this.http.get<Article>("/api/article/" + this.id, this.httpOptions)
  }

  async reloadComments() {
    await this.http.get<Comment[]>("/api/comment/article/" + this.id, this.httpOptions).subscribe((response: any) => {
      if (response.length == 0) {
        this.noComments = true;
        this.comments = new Array<Comment>();
      } else {
        this.comments = new Array<Comment>();
        this.hideReplyTextArea = [];
        for (let i = 0; i < response.length; i++) {
          this.hideReplyTextArea.push(false);
        }
        response.forEach((tempComment: any) => {
          let tempLikesArr = new Array<Like>();
          let tempChildrenArr = new Array<Comment>();
          let commentUser = new User(tempComment.user.id, tempComment.user.firstName, tempComment.user.lastName, tempComment.user.photoLink);
          tempComment.likes.forEach((like: any) => {
            tempLikesArr.push(new Like(like.id, new User(like.user.id, like.user.firstName, like.user.lastName, like.user.photoLink), like.liked));
          });
          tempComment.children.forEach((child: any) => {
            tempChildrenArr.push(new Comment(child.id, child.content, [], new User(child.user.id, child.user.firstName, child.user.lastName, child.user.photoLink), child.likes, child.publishedDate, child.edited));
          });
          this.comments.push(new Comment(tempComment.id, tempComment.content, tempChildrenArr, commentUser, tempLikesArr, tempComment.publishedDate, tempComment.edited));
          if (tempComment.id == this.repliedComment.id) {
            this.repliedComment.copy(this.comments[this.comments.length - 1]);
          }
        });
        this.noComments = false;
      }
    });
  }

  async addReaction(comment: Comment, reaction: boolean) {
    console.log("add reaction");
    console.log(this.userId);
    console.log(reaction);
    let temp = -1;
    comment.likes.forEach((like: Like) => {
      if (this.userId == like.user.id) {
        temp = comment.likes.indexOf(like);
      }
    });
    if (temp >= 0 && comment.likes[temp].liked == reaction) {
      await this.http.delete("/api/likes/" + comment.likes[temp].id, this.httpOptions).subscribe((response: any) => {
          console.log(response)
          this.reloadComments();
        }
      );
    } else {
      await this.http.post("/api/likes", {
        "comment": {"id": comment.id},
        "user": {"id": this.userId},
        "liked": reaction
      }, this.httpOptions).subscribe(
        (response: any) => {
          console.log(response)
          this.reloadComments();
        }
      );
    }
  }

  manageRepliedBlock(ind: number) {
    this.reply = "";
    this.hideReplyTextArea[ind] = !this.hideReplyTextArea[ind];
    for (let i = 0; i < this.hideReplyTextArea.length; i++) {
      if (i != ind) {
        this.hideReplyTextArea[i] = false;
      }
    }
  }

  showAllReplies(comment: Comment) {
    this.repliedComment = comment;
    this.showRepliedMessages = !this.showRepliedMessages;
    this.reply = "";
    this.commentContent = "";
    this.edit = false;
  }

  hideAllReplies() {
    this.showRepliedMessages = false;
    this.reply = "";
    for (let i = 0; i < this.hideReplyTextArea.length; i++) {
      this.hideReplyTextArea[i] = false;
    }
    this.edit = false;
  }

  userLiked(comment: Comment) {
    for (let i = 0; i < comment.likes.length; i++) {
      if (comment.likes[i].user.id == this.userId) {
        return comment.likes[i].liked;
      }
    }
    return false;
  }

  userDisliked(comment: Comment) {
    for (let i = 0; i < comment.likes.length; i++) {
      if (comment.likes[i].user.id == this.userId) {
        return !comment.likes[i].liked;
      }
    }
    return false;
  }

  publishComment() {
    if (this.validateComment(this.commentContent)) {
      this.http.post("/api/comment", {
        "article": {"id": this.id},
        "user": {"id": this.userId},
        "content": this.commentContent
      }, this.httpOptions).subscribe(
        (response: any) => {
          console.log(response)
          this.reloadComments();
        }
      );
      this.commentContent = "";
    } else {
      this.commentContent = "";
      Swal.fire({
        icon: 'error',
        text: 'Comment is empty',
        showConfirmButton: false,
        timer: 1500
      });
    }
  }

  publishReply(comment: Comment) {
    if (this.validateComment(this.reply)) {
      this.http.post("/api/comment", {
        "article": {"id": this.id},
        "user": {"id": this.userId},
        "content": this.reply,
        "parent": {"id": comment.id}
      }, this.httpOptions).subscribe(
        (response: any) => {
          console.log(response)
          this.reloadComments();
        }
      );
      this.reply = "";
    } else {
      this.reply = "";
      Swal.fire({
        icon: 'error',
        text: 'Reply is empty',
        showConfirmButton: false,
        timer: 1500
      });
      this.manageRepliedBlock(this.comments.indexOf(comment));
    }
  }

  validateComment(context: string) {
    return (context.replace(/\s/g, "").length > 0);
  }

  deleteComment(commentId: number) {
    Swal.fire({
      text : 'Are you sure?' ,
      icon : 'warning',
      showCancelButton: true,
      confirmButtonColor: '#E02232',
      cancelButtonColor: '#E02232',
      confirmButtonText: 'Delete'
    }).then((result) => {
      if (result.isConfirmed) {
        this.http.delete("/api/comment/" + commentId, this.httpOptions).subscribe((response: any) => {
            console.log(response)
            this.reloadComments();
          }
        );
      }
    })
  }

  editComment(comment: Comment) {
    for (let i = 0; i < this.hideReplyTextArea.length; i++) {
      this.hideReplyTextArea[i] = false;
    }
    this.edit = true;
    if(this.showRepliedMessages){
      this.reply = comment.content;
    }
    else{
      this.commentContent = comment.content;
    }
    this.editCommentContent = comment;
  }



  submitEdit(text: string) {
    if (this.validateComment(text)) {
      this.http.put("/api/comment/" + this.editCommentContent.id, {
        "content": text
      }, this.httpOptions).subscribe(
        (response: any) => {
          console.log(response)
          this.reloadComments();
        }
      );
      this.edit = false;
      this.commentContent = "";
      this.reply = "";
    }
    else {
      this.edit = false;
      this.commentContent = "";
      this.reply = "";
      Swal.fire({
        icon: 'error',
        text: 'Comment is empty',
        showConfirmButton: false,
        timer: 1500
      });
    }
  }

  cancelComment(){
    this.edit = false;
    this.commentContent = "";
    this.reply = "";
  }

}
