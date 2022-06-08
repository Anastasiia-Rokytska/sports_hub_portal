import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeftSideMenuAdminComponent } from './left-side-menu-admin.component';

describe('LeftSideMenuAdminComponent', () => {
  let component: LeftSideMenuAdminComponent;
  let fixture: ComponentFixture<LeftSideMenuAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeftSideMenuAdminComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LeftSideMenuAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
