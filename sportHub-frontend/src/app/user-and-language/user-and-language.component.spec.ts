import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserAndLanguageComponent } from './user-and-language.component';

describe('UserAndLanguageComponent', () => {
  let component: UserAndLanguageComponent;
  let fixture: ComponentFixture<UserAndLanguageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserAndLanguageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserAndLanguageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
