import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShareNetworksComponent } from './share-networks.component';

describe('ShareNetworksComponent', () => {
  let component: ShareNetworksComponent;
  let fixture: ComponentFixture<ShareNetworksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShareNetworksComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShareNetworksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
