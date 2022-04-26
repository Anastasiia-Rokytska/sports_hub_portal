import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'leftside-menu',
  templateUrl: './leftside-menu.component.html',
  styleUrls: ['./leftside-menu.component.css']
})
export class LeftsideMenuComponent implements OnInit {
  nbaMenu = "display: none";
  nbaAFCSouthMenu = "display: none";

  constructor() { }

  ngOnInit(): void {
  }

  hideAll(){
    this.hideThirdMenu();
    this.hideSecondMenu();
  }
  hideSecondMenu(){
    this.nbaMenu = "display: none";
  }
  hideThirdMenu(){
    this.nbaAFCSouthMenu = "display: none";
  }

  menuVisibility(menu: string) {
    if(menu == "home"){
      this.hideAll();
    } else if (menu === "nba") {
      this.hideAll();
      this.nbaMenu = "display: block";
    }else if(menu === "nba-AFC-south"){
      this.nbaAFCSouthMenu = "display: block";
    }
  }


}
