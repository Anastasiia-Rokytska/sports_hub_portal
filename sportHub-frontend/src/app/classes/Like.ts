import {User} from "./User";

export class Like{
  id!: number;
  user !: User;
  liked !: boolean;

  constructor(id: number, user: User, liked: boolean){
    this.id = id
    this.user = user
    this.liked = liked
  }

  printInfo(){
    console.log("id : " + this.id)
    console.log("user : " + this.user)
    console.log("liked : " + this.liked)
  }

}
