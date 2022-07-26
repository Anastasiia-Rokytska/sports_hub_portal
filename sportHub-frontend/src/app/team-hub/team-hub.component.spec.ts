import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamHubComponent } from './team-hub.component';

describe('TeamHubComponent', () => {
  let component: TeamHubComponent;
  let fixture: ComponentFixture<TeamHubComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TeamHubComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TeamHubComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
