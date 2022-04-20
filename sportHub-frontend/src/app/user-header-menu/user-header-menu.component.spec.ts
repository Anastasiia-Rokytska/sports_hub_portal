import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserHeaderMenuComponent } from './user-header-menu.component';

describe('UserHeaderMenuComponent', () => {
  let component: UserHeaderMenuComponent;
  let fixture: ComponentFixture<UserHeaderMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserHeaderMenuComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserHeaderMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
