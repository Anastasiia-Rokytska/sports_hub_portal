import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeftsideMenuComponent } from './leftside-menu.component';

describe('LeftsideMenuComponent', () => {
  let component: LeftsideMenuComponent;
  let fixture: ComponentFixture<LeftsideMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeftsideMenuComponent ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LeftsideMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
