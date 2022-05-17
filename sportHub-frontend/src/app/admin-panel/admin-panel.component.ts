import {Component, OnInit, ViewChildren} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import { InputComponent } from '../components/input/input/input.component';
import {Observable} from "rxjs";

interface MenuItem {
  id: number;
  hidden: boolean;
  name: string;
  parentId: number;
}

@Component({
  selector: 'admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})

export class AdminPanelComponent implements OnInit {
  // variables to show and hide the menu
  secondMenuVisible: boolean = false;
  thirdMenuVisible: boolean = false;

  // variables to store new menu items
  editedItems = new Map<number, MenuItem>();
  deletedItems = new Map<number, number[]>();
  newItems = new Map<number, MenuItem[]>();

  //variables to store menu items from db
  menuItems: MenuItem[] = [];
  subcategory: MenuItem[] = [];
  teams: MenuItem[] = [];

  //variables to store id's of parent items
  lastChosenCategory: number = 0;
  lastChosenSubcategory: number = 0;

  //variables to show/hide modal
  showModal: boolean = false;
  ModalEdit: boolean = false;
  ModalDelete: boolean = false;
  title: string = '';
  tempId: number = 0;
  tempIdNewItem: number =20000;
  response: Observable<string> | undefined;
  tempItem: MenuItem = {} as MenuItem;

  constructor(private http: HttpClient) {}

  @ViewChildren(InputComponent) inputs: InputComponent[] = [];

  //function to show main categories
  async showMainCategories(){
    await this.http.get<MenuItem[]>('/api/category/parent/' + 0).subscribe(data => {
      this.menuItems = [...data];

      if(this.newItems.has(0)){
        let temp = this.newItems.get(0);
        for(let i = 0; i < temp!.length;i++){
          this.menuItems.push(temp![i]);
        }
      }

      for(let i = 0 ; i < this.menuItems.length ; i++){
        if(this.editedItems.has(this.menuItems[i].id)){
          this.menuItems[i] = Object.assign({},this.editedItems.get(this.menuItems[i].id));
        }
      }

      if(this.deletedItems.has(0)){
        let temp = this.deletedItems.get(0);
        for(let i = 0 ; i < this.menuItems.length ; i++){
          if(temp!.indexOf(this.menuItems[i].id) !== -1){
            this.menuItems.splice(i,1);
            i--;
          }
        }
      }

      this.menuItems = this.menuItems.reverse();

    });
  }

  //function to show sub categories
  async showSubcategoryItems(id : number){
    this.thirdMenuVisible = false;
    this.secondMenuVisible = true;
    await this.http.get<MenuItem[]>('/api/category/parent/' + id).subscribe(data => {
      this.subcategory = [...data];

      if(this.newItems.has(id)){
        let temp = this.newItems.get(id);
        for(let i = 0; i < temp!.length;i++){
          this.subcategory.push(temp![i]);
        }
      }

      for(let i = 0 ; i < this.subcategory.length ; i++){
        if(this.editedItems.has(this.subcategory[i].id)){
          this.subcategory[i] = Object.assign({},this.editedItems.get(this.subcategory[i].id));
        }
      }

      if(this.deletedItems.has(id)){
        let temp = this.deletedItems.get(id);
        for(let i = 0 ; i < this.subcategory.length ; i++){
          if(temp!.indexOf(this.subcategory[i].id) !== -1){
            this.subcategory.splice(i,1);
            i--;
          }
        }
      }

      this.subcategory = this.subcategory.reverse();
    });
    this.lastChosenCategory = id;
  }

  //function to show teams
  async showTeamItems(id : number){
    await this.http.get<MenuItem[]>('/api/category/parent/' + id).subscribe(data => {
      this.teams = [...data];

      if(this.newItems.has(id)){
        let temp = this.newItems.get(id);
        for(let i = 0; i < temp!.length;i++){
          this.teams.push(temp![i]);
        }
      }

      for(let i = 0 ; i < this.teams.length ; i++){
        if(this.editedItems.has(this.teams[i].id)){
          this.teams[i] = Object.assign({},this.editedItems.get(this.teams[i].id));
        }
      }

      if(this.deletedItems.has(id)){
        let temp = this.deletedItems.get(id);
        for(let i = 0 ; i < this.teams.length ; i++){
          if(temp!.indexOf(this.teams[i].id) !== -1){
            this.teams.splice(i,1);
            i--;
          }
        }
      }

      this.teams = this.teams.reverse();
    });
    this.thirdMenuVisible = true;
    this.lastChosenSubcategory = id;
  }

  //function to hide/show menu items
  hideShowItems(item : MenuItem) {
    let keyItem = item.id;
    let tempItem: MenuItem = item;
    if(this.editedItems.has(keyItem)){
      tempItem = this.editedItems.get(keyItem)!;
    }
    tempItem.hidden = !tempItem.hidden;
    this.editedItems.set(keyItem, tempItem);
    this.reloadItems(item.parentId);
  }

  //function to show modal
  showModalWindowAddItems(id : number){
    if(id === 0){
      this.showModal = true;
      this.title = 'Add Category';
      this.tempId = 0;
    }
    else if(id === 1){
      this.showModal = true;
      this.title = 'Add Subcategory';
      this.tempId = this.lastChosenCategory;
    }
    else if(id === 2){
      this.showModal = true;
      this.title = 'Add Team';
      this.tempId = this.lastChosenSubcategory;
    }
  }

  //function to add new item
  submitButtonAddItems(){
    let categoryName = Array.from(this.inputs)[0].value;
    let tempNewItems: MenuItem[] = [];
    if(this.newItems.has(this.tempId)){
      tempNewItems = this.newItems.get(this.tempId)!;
    }
    let newItem: MenuItem = {
      id: this.tempIdNewItem++,
      hidden: false,
      name: categoryName,
      parentId: this.tempId
    }
    tempNewItems.push(newItem);
    this.newItems.set(this.tempId, tempNewItems);
    this.hideModalWindow();
    this.reloadItems(this.tempId);
    console.log(this.newItems);
  }

  submitButtonEditItems(){
    let newName = Array.from(this.inputs)[0].value;
    this.tempItem.name = newName;
    this.editedItems.set(this.tempItem.id, this.tempItem);
    this.hideModalWindow();
    this.reloadItems(this.tempItem.parentId);
    console.log(this.editedItems);
  }

  deleteItemModal(item : MenuItem){
    this.tempItem = item;
    this.showModal = true;
    this.ModalDelete = true;
    this.title = this.tempItem.name;
  }

  editItemModal(item : MenuItem){
    this.tempItem = item;
    this.showModal = true;
    this.ModalEdit = true;
    this.title = 'Edit Item';
  }

  //function to delete item
  submitButtonDeleteItems(){
    let tempDeletedItems: number[] = [];
    if(this.deletedItems.has(this.tempItem.parentId)){
      tempDeletedItems = this.deletedItems.get(this.tempItem.parentId)!;
    }
    tempDeletedItems.push(this.tempItem.id);
    this.deletedItems.set(this.tempItem.parentId, tempDeletedItems);
    let tempItems: MenuItem[] = [];
    tempDeletedItems = [];
    this.http.get<MenuItem[]>('/api/category/parent/' + this.tempItem.id).subscribe(data => {
      tempItems = [...data];
      if(this.deletedItems.has(this.tempItem.id)){
        tempDeletedItems = this.deletedItems.get(this.tempItem.id)!;
      }
      for(let i = 0 ; i < tempItems.length ; i++){
        tempDeletedItems.push(tempItems[i].id);
      }
      this.deletedItems.set(this.tempItem.id, tempDeletedItems);
    });
    this.reloadItems(this.tempItem.parentId);
    this.hideModalWindow();
  }

  //function to reload items
  reloadItems(id : number){
    if(id === 0){
      this.showMainCategories();
    }
    else if(id === this.lastChosenCategory){
      this.showSubcategoryItems(this.lastChosenCategory);
    }
    else{
      this.showTeamItems(this.lastChosenSubcategory);
    }
  }

  //function to hide modal
  hideModalWindow(){
    this.showModal = false;
    this.ModalEdit = false;
    this.ModalDelete = false;
    Array.from(this.inputs)[0].value = '';
  }

  ngOnInit(): void {
    this.showMainCategories();
  }

  saveChanges(){
    this.newItems.forEach((value: MenuItem[], key: number) => {
      let temp = value;
      for(let i = 0 ; i < temp.length ; i++){
        let name = temp[i].name;
        let parentId = key;
        let hidden = temp[i].hidden;
        let body = JSON.stringify({name : name,parentId : parentId, hidden : hidden});
        const httpOptions = {
          headers: new HttpHeaders({'Content-Type': 'application/json', 'Accept': 'application/json'})
        }
        this.http.post<MenuItem>("/api/category", body, httpOptions).subscribe(
          (data) => {
            console.log(data);
          },
          (error) => {
            console.log(error);
          }
        );
      }
    });

    this.editedItems.forEach((value: MenuItem, key: number) => {
      let name = value.name;
      let hidden = value.hidden;
      let body = JSON.stringify({name : name, hidden : hidden});
      const httpOptions = {
        headers: new HttpHeaders({'Content-Type': 'application/json', 'Accept': 'application/json'})
      }
      this.http.put<MenuItem>("/api/category/" + key, body, httpOptions).subscribe(
        (data) => {
          console.log(data);
        },
        (error) => {
          console.log(error);
        }
      );
    });

    this.deletedItems.forEach((value: number[], key: number) => {
      let temp = value;
      for(let i = 0 ; i < temp.length ; i++){
        this.http.delete<String>("/api/category/" + temp[i]).subscribe(
          (data) => {
            console.log(data);
          },
          (error) => {
            console.log(error);
          }
        );
      }
    });

    this.newItems.clear();
    this.editedItems.clear();
    this.deletedItems.clear();
  }
}
