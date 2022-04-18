import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LargeButtonComponent } from './large-button.component';

describe('LargeButtonComponent', () => {
  let component: LargeButtonComponent;
  let fixture: ComponentFixture<LargeButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LargeButtonComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LargeButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
