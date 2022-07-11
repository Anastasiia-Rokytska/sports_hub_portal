import {Like} from "./Like";
import {User} from "./User";

export class Comment{
  id !: number;
  content !: string;
  children !: Comment[];
  user !: User;
  likes !: Like[];
  publishedDate !: string;
  edited: boolean = false;

  amountOfLikes : number = 0;
  amountOfDislikes :number = 0;

  constructor(id: number, content: string, children: Comment[], user: User, likes: Like[], publishedDate: string, edited: boolean){
    this.id = id
    this.content = content
    this.children = children
    this.user = user
    this.likes = likes
    this.publishedDate = publishedDate
    this.edited = edited
    this.countLikes();
  }
  countLikes(){
    this.likes.forEach(like => {
      if(like.liked){
        this.amountOfLikes++
      }
      else{
        this.amountOfDislikes++
      }
    });
  }

  copy(com: Comment){
    this.id = com.id
    this.content = com.content
    this.children = com.children
    this.user = com.user
    this.likes = com.likes
    this.publishedDate = com.publishedDate
    this.amountOfLikes = com.amountOfLikes
    this.amountOfDislikes = com.amountOfDislikes
    this.edited = com.edited
  }
}
