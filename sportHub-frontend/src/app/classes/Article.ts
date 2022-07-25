interface MenuItem {
  id: number;
  hidden: boolean;
  name: string;
  parentId: number;
}
export class Article{
  id!: number;
  title!: string;
  caption!: string;
  author!: string;
  categories!: MenuItem[];
  content!: string;
  publishedDate!: string;
  isPublished!: boolean;
  commentable!: boolean;
  language!: string;
  icon: string | null;

  constructor(id: number, title: string, caption: string, author: string, categories: MenuItem[], content: string, publishedDate: string, isPublished: boolean, commentable: boolean, language: string, icon: string | null){
    this.id = id;
    this.title = title;
    this.caption = caption;
    this.author = author;
    this.categories = categories;
    this.content = content;
    this.publishedDate = publishedDate;
    this.isPublished = isPublished;
    this.commentable = commentable;
    this.language = language;
    if(icon == null){
      this.icon = '../../../assets/images/userPhoto.jpg'
    }
    else{
      this.icon = 'data:image/png;base64,' + icon;
    }
  }
}
