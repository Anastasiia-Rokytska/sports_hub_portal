import { Component, Inject, OnInit } from '@angular/core';
import { DynamicService } from 'src/app/dynamic.service';
import { StringInjector } from 'src/app/teams/teams/teams.component';

@Component({
  selector: 'app-drop-down',
  templateUrl: './drop-down.component.html',
  styleUrls: ['./drop-down.component.css']
})
export class DropDownComponent implements OnInit {

  options: Array<string> = new Array()
  nameField: string = 'Field'

  selectedOption!: string

  constructor(@Inject(StringInjector) options: StringInjector[],
              private dynamicService: DynamicService) {
    this.nameField = options[0].name
    for (let i = 1; i < options.length; i++) this.options.push(options[i].name)
    dynamicService.data.clearForm.subscribe((data: boolean) => {
      this.selectedOption = this.options[0]
      this.selectOption()
    })
  }

  ngOnInit(): void {
    if (this.options.length > 0) this.selectedOption = this.options[0]
  }

  selectOption(){
    if (this.dynamicService.data.selectedCategory != undefined ) {
      this.dynamicService.data.selectedCategory.next({name: this.nameField, value: this.selectedOption})
    }
  }

}
