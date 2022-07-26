import {Component, OnInit, ViewChildren} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {InputComponent} from '../components/input/input/input.component';
import Swal from "sweetalert2";


interface MenuItem {
  id: number;
  hidden: boolean;
  name: string;
  parentId: number;
}

const AllTeamsId: number = 13;

@Component({
  selector: 'admin-panel-categories',
  templateUrl: './admin-panel-categories.component.html',
  styleUrls: ['./admin-panel-categories.component.css']
})

export class AdminPanelCategoriesComponent implements OnInit {
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
  tempIdNewItem: number = 20000;
  newParentId = new Map<number, number>();

  userName: string = "";
  userEmail: string = "";


  constructor(private http: HttpClient) {
  }

  @ViewChildren(InputComponent) inputs: InputComponent[] = [];

  //function to show main categories
  async showMainCategories() {
    await this.http.get<MenuItem[]>('/api/category/editor').subscribe(data => {
      this.menuItems = [...data];

      if (this.newItems.has(0)) {
        let temp = this.newItems.get(0);
        for (let i = 0; i < temp!.length; i++) {
          this.menuItems.push(temp![i]);
        }
      }

      for (let i = 0; i < this.menuItems.length; i++) {
        if (this.editedItems.has(this.menuItems[i].id)) {
          this.menuItems[i] = Object.assign({}, this.editedItems.get(this.menuItems[i].id));
        }
      }

      if (this.deletedItems.has(0)) {
        let temp = this.deletedItems.get(0);
        for (let i = 0; i < this.menuItems.length; i++) {
          if (temp!.indexOf(this.menuItems[i].id) !== -1) {
            this.menuItems.splice(i, 1);
            i--;
          }
        }
      }
      this.menuItems.forEach(item => {
        item.parentId = 0;
      });
    });
  }

  //function to show sub categories
  async showSubcategoryItems(id: number) {
    this.thirdMenuVisible = false;
    this.secondMenuVisible = true;
    await this.http.get<MenuItem[]>('/api/category/parent/all/' + id).subscribe(data => {
      this.subcategory = [...data];

      if (this.newItems.has(id)) {
        let temp = this.newItems.get(id);
        for (let i = 0; i < temp!.length; i++) {
          this.subcategory.push(temp![i]);
        }
      }

      for (let i = 0; i < this.subcategory.length; i++) {
        if (this.editedItems.has(this.subcategory[i].id)) {
          this.subcategory[i] = Object.assign({}, this.editedItems.get(this.subcategory[i].id));
        }
      }

      if (this.deletedItems.has(id)) {
        let temp = this.deletedItems.get(id);
        for (let i = 0; i < this.subcategory.length; i++) {
          if (temp!.indexOf(this.subcategory[i].id) !== -1) {
            this.subcategory.splice(i, 1);
            i--;
          }
        }
      }
      this.subcategory.forEach(item => {
        item.parentId = id;
      })
    });
    this.lastChosenCategory = id;
  }

  //function to show teams
  async showTeamItems(id: number) {
    if (id == AllTeamsId) {
      id = this.lastChosenCategory;
    }
    this.thirdMenuVisible = true;
    await this.http.get<MenuItem[]>('/api/category/teams/' + id).subscribe(data => {
      this.teams = [...data];

      for (let i = 0; i < this.teams.length; i++) {
        if (this.editedItems.has(this.teams[i].id)) {
          this.teams[i] = Object.assign({}, this.editedItems.get(this.teams[i].id));
        }
      }
      this.lastChosenSubcategory = id;
    });
  }

  //function to hide/show menu items
  hideShowItems(item: MenuItem) {
    let keyItem = item.id;
    let tempItem: MenuItem = item;
    if (this.editedItems.has(keyItem)) {
      tempItem = this.editedItems.get(keyItem)!;
    }
    tempItem.hidden = !tempItem.hidden;
    this.editedItems.set(keyItem, tempItem);
    this.reloadItems(item.parentId);
  }

  //function to show modal
  showModalWindowAddItems(id: number) {
    let title = 'title'
    let tempId = 0
    if (id === 0) {
      title = 'Add Category';
    } else if (id === 1) {
      title = 'Add Subcategory';
      tempId = this.lastChosenCategory;
    }
    Swal.fire({
      title: title,
      input: "text",
      inputPlaceholder: 'Name your menu item',
      showCancelButton: true,
      confirmButtonColor: '#E02232',
      cancelButtonColor: '#E02232',
      confirmButtonText: 'Add',
    }).then((result) => {
      if (result.isConfirmed) {
        console.log(result.value);
        this.submitButtonAddItems(tempId, result.value.toString());
      }
    })
  }

  redirectToTeamPage() {
    window.open('/admin/teams', '_blank');
  }

  //function to add new item
  submitButtonAddItems(id: number, name: string) {
    let tempNewItems: MenuItem[] = [];
    if (this.newItems.has(id)) {
      tempNewItems = this.newItems.get(id)!;
    }
    let newItem: MenuItem = {
      id: this.tempIdNewItem++,
      hidden: false,
      name: name,
      parentId: id
    }
    tempNewItems.push(newItem);
    this.newItems.set(id, tempNewItems);
    this.reloadItems(id);
    console.log(this.newItems)
  }

  submitButtonEditItems(item: MenuItem, name: string) {
    item.name = name;
    this.editedItems.set(item.id, item);
    this.reloadItems(item.parentId);
  }

  deleteItemModal(item: MenuItem) {
    Swal.fire({
      title: 'Are you sure?',
      text: 'You are going to delete ' + item.name,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#E02232',
      cancelButtonColor: '#E02232',
      confirmButtonText: 'Delete'
    }).then((result) => {
      if (result.isConfirmed) {
        this.submitButtonDeleteItems(item);
      }
    })
  }

  async editItemModal(item: MenuItem) {
    Swal.fire({
      title: 'Edit item',
      input: "text",
      inputPlaceholder: 'Name your menu item',
      showCancelButton: true,
      confirmButtonColor: '#E02232',
      cancelButtonColor: '#E02232',
      confirmButtonText: 'Edit',
    }).then((result) => {
      if (result.isConfirmed) {
        console.log(result.value);
        this.submitButtonEditItems(item, result.value.toString());
      }
    })
  }

  //function to delete item
  submitButtonDeleteItems(item: MenuItem) {
    let tempDeletedItems: number[] = [];
    console.log(item.parentId)
    if (this.deletedItems.has(item.parentId)) {
      tempDeletedItems = this.deletedItems.get(item.parentId)!;
    }
    tempDeletedItems.push(item.id);
    this.deletedItems.set(item.parentId, tempDeletedItems);
    let tempItems: MenuItem[] = [];
    tempDeletedItems = [];
    this.http.get<MenuItem[]>('/api/category/parent/all/' + item.id).subscribe(data => {
      tempItems = [...data];
      if (this.deletedItems.has(item.id)) {
        tempDeletedItems = this.deletedItems.get(item.id)!;
      }
      for (let i = 0; i < tempItems.length; i++) {
        tempDeletedItems.push(tempItems[i].id);
      }
      this.deletedItems.set(item.id, tempDeletedItems);
    });
    this.reloadItems(item.parentId);
  }

  //function to reload items
  reloadItems(id: number) {
    this.showMainCategories();
    this.secondMenuVisible = false;
    this.thirdMenuVisible = false;
  }

  ngOnInit(): void {
    this.http.get<MenuItem[]>('/api/category').subscribe(data => {
      if (data.length > 0) {
        this.tempIdNewItem = data[data.length - 1].id + 1;
      } else {
        this.tempIdNewItem = 0;
      }
    });
    this.showMainCategories();
    this.getUser().subscribe((response: any) => {
      console.log("Response: ", response)
      this.userName = response.firstName + ' ' + response.lastName
      if (this.userName.length > 19) {
        this.userName = this.userName.slice(0, 16) + '...'
      }
      this.userEmail = response.email
    }, (error) => {
      console.log("Error: ", error.error)
    })
  }

  getUser() {
    const httpOptions = {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'Accept': 'application/json'})
    }
    return this.http.get("user/own_information", httpOptions)
  }

  async saveChanges() {
    await this.newItems.forEach((value: MenuItem[], key: number) => {
      let temp = value;
      for (let i = 0; i < temp.length; i++) {
        let formData = new FormData();
        formData.append('id', temp[i].id.toString());
        formData.append('name', temp[i].name);
        formData.append('hidden', temp[i].hidden.toString());
        formData.append('team', false.toString());
        formData.append('parentCategoryId', key.toString());
        this.http.post<MenuItem>("/api/category", formData).subscribe(
          (data) => {
            console.log(data)
          },
          (error) => {
            console.log(error);
          }
        );
      }
    });

    await this.editedItems.forEach((value: MenuItem, key: number) => {
      let formData = new FormData();
      formData.append('id', value.id.toString());
      formData.append('name', value.name);
      formData.append('hidden', value.hidden.toString());
      formData.append('team', false.toString());
      this.http.put<MenuItem>("/api/category/" + key, formData).subscribe(
        (data) => {
          console.log(data);
        },
        (error) => {
          console.log(error);
        }
      );
    });

    await this.deletedItems.forEach((value: number[], key: number) => {
      let temp = value;
      for (let i = 0; i < temp.length; i++) {
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

    Swal.fire({
      icon: 'success',
      title: 'Successfully saved',
      showConfirmButton: false,
      timer: 1500
    })
  }
}
