import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminPanelManageArticlesComponent } from './admin-panel-manage-articles.component';

describe('AdminPanelManageArticlesComponent', () => {
  let component: AdminPanelManageArticlesComponent;
  let fixture: ComponentFixture<AdminPanelManageArticlesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminPanelManageArticlesComponent ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminPanelManageArticlesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
