import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminPanelArticlesComponent } from './admin-panel-articles.component';

describe('AdminPanelArticlesComponent', () => {
  let component: AdminPanelArticlesComponent;
  let fixture: ComponentFixture<AdminPanelArticlesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminPanelArticlesComponent ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminPanelArticlesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
