import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminPanelCategoriesComponent } from './admin-panel-categories.component';

describe('AdminPanelComponent', () => {
  let component: AdminPanelCategoriesComponent;
  let fixture: ComponentFixture<AdminPanelCategoriesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminPanelCategoriesComponent ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminPanelCategoriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
