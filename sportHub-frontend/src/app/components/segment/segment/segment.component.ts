import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-segment',
  templateUrl: './segment.component.html',
  styleUrls: ['./segment.component.css']
})
export class SegmentComponent implements OnInit {

  @Input() class : string = 'nonactive_segment'
  @Input() text: string | null = null

  constructor() { }

  ngOnInit(): void {
  }

}
