import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Component, OnInit, ViewChildren} from '@angular/core';
import {InputComponent} from "../components/input/input/input.component";
import Swal from "sweetalert2";

interface MenuItem{
  id: number;
  hidden: boolean;
  name: string;
  parentId: number;
}
interface team{
  id: number;
  name: string
}

@Component({
  selector: 'admin-panel-articles',
  templateUrl: './admin-panel-articles.component.html',
  styleUrls: ['./admin-panel-articles.component.css']
})
export class AdminPanelArticlesComponent implements OnInit {

  constructor(
    private http: HttpClient
  ) {}

  @ViewChildren(InputComponent) inputs: InputComponent[] = [];

  title: string = 'Choose category';
  categories: MenuItem[] = [];
  selectedCategoryId: number = -1;
  selectedSubcategoryId: number = -1;
  selectedTeamId: number = -1;
  subcategories: MenuItem[] = [];
  teams: Array<team> = [];
  articleContent: string = '';
  preview: boolean = false;
  previewMode: string = '';
  caption: string = '';
  headline: string = '';
  language: string = 'English';
  publishedDate: string = '';
  errorMessage: boolean = false;
  userName: string = '';
  userEmail: string = '';
  userId: string = '';
  commentable: boolean = true;
  imageUrl: string | undefined;
  image = '../../../assets/images/userPhoto.jpg';

  ngOnInit(): void {
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
      await this.http.get<Array<team>>('/team/' + subcategoryId).subscribe(data => {
        this.teams = data
      })
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

  previewArticle(){
    console.log("subcategory: " + this.selectedSubcategoryId);
    console.log("team: " + this.selectedTeamId);
    console.log(this.articleContent.replace(/\n+?/g, '<br>'));
    this.headline = Array.from(this.inputs)[0].value;
    this.caption = Array.from(this.inputs)[1].value;
    if(this.articleContent.length > 0 && this.headline.length > 0 && this.caption.length > 0){
      this.errorMessage = false;
      this.showPreview();
    }
    else{
      Swal.fire({
        icon: 'warning',
        title: 'Headline, caption and content are required!',
        showConfirmButton: false,
        timer: 1500
      })
    }
  }

  showPreview(){
    this.previewMode = this.articleContent.replace(/\n+?/g, '<br>');
    this.publishedDate = new Date().toLocaleDateString();
    this.preview = true;
  }
  backToEditor(){
    this.preview = false;
  }
  handleLanguageChanges(value: string){
    this.language = value;
  }

  cancelChanges(){
    Swal.fire({
      title : 'You are going to discard changes!',
      text : 'Entered information will be missed!\nAre you sure?',
      icon : 'warning',
      showCancelButton: true,
      confirmButtonColor: '#E02232',
      cancelButtonColor: '#E02232',
      confirmButtonText: 'Yes'
    }).then((result) => {
      if (result.isConfirmed) {
        this.emptyFields();
      }
    });
  }

  emptyFields(){
    this.caption = '';
    this.headline = '';
    this.publishedDate = '';
    this.articleContent = '';
    this.selectedCategoryId = -1;
    this.selectedSubcategoryId = -1;
    this.selectedTeamId = -1;
    this.title = 'Choose category';
    this.imageUrl = undefined;
    this.image = '../../../assets/images/userPhoto.jpg';
  }

  async saveArticle(){
    this.headline = Array.from(this.inputs)[0].value;
    this.caption = Array.from(this.inputs)[1].value;
    this.publishedDate = new Date().toLocaleDateString();
    if(this.articleContent.length > 0 && this.headline.length > 0 && this.caption.length > 0){
      let tempDate: string[] = this.publishedDate.split('.');
      this.publishedDate = tempDate[2] + '-' + tempDate[1] + '-' + tempDate[0];
      this.errorMessage = false;
      this.previewMode = this.articleContent.replace(/\n+?/g, '<br>');
      let formData = new FormData();
      formData.append('title', Array.from(this.inputs)[0].value);
      formData.append('content', this.previewMode);
      formData.append('author', this.userName);
      formData.append('commentable', this.commentable.toString());
      formData.append('language', this.language);
      formData.append('caption', Array.from(this.inputs)[1].value);
      if(this.imageUrl != undefined){
        formData.append('icon', this.imageUrl);
      }
      formData.append('selectedCategory', this.selectedCategoryId.toString());
      formData.append('selectedSubCategory', this.selectedSubcategoryId.toString());
      formData.append('selectedTeam', this.selectedTeamId.toString());
      await this.http.post("/api/article", formData).subscribe(
        () => {
          Swal.fire({
            icon: 'success',
            title: 'Successfully saved',
            showConfirmButton: false,
            timer: 1500
          });
          this.emptyFields();
        },
        (error) => {
          console.log(error);
          Swal.fire({
            icon: 'warning',
            title: 'Something went wrong',
            showConfirmButton: false,
            timer: 1500
          })
        },
      );
    }
    else{
      Swal.fire({
        icon: 'warning',
        title: 'Headline, caption and content are required',
        showConfirmButton: false,
        timer: 1500
      })
    }

  }
  loadPhoto(event: any){
    let reader = new FileReader()
    this.imageUrl = event.target.files[0]
    reader.readAsDataURL(event.target.files[0])
    reader.onload = (readerEvent: any) => {
      this.image = readerEvent.target.result
    }
  }
}
