import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllTeamsComponent } from './all-teams.component';

describe('AllTeamsComponent', () => {
  let component: AllTeamsComponent;
  let fixture: ComponentFixture<AllTeamsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllTeamsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AllTeamsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
