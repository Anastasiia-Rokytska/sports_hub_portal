import {EventEmitter, Injectable} from '@angular/core';
import {Subscription} from "rxjs/internal/Subscription";

@Injectable({
  providedIn: 'root'
})
export class EventEmitterService {

  invokeArticlesRender = new EventEmitter();
  // @ts-ignore
  subsVar: Subscription;

  constructor() { }

  RenderArticles(temp_arr: Map<number,string>[]) {
    this.invokeArticlesRender.emit(temp_arr);
  }
}
