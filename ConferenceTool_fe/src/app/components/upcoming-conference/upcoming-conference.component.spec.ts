import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpcomingConferenceComponent } from './upcoming-conference.component';

describe('UpcomingConferenceComponent', () => {
  let component: UpcomingConferenceComponent;
  let fixture: ComponentFixture<UpcomingConferenceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpcomingConferenceComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(UpcomingConferenceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
