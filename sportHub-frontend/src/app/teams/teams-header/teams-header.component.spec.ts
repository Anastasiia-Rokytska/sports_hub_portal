import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamsHeaderComponent } from './teams-header.component';

describe('TeamsHeaderComponent', () => {
  let component: TeamsHeaderComponent;
  let fixture: ComponentFixture<TeamsHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TeamsHeaderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TeamsHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
