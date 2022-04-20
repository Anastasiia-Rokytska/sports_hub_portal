import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WhiteSmallButtonComponent } from './white-small-button.component';

describe('WhiteSmallButtonComponent', () => {
  let component: WhiteSmallButtonComponent;
  let fixture: ComponentFixture<WhiteSmallButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WhiteSmallButtonComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WhiteSmallButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
