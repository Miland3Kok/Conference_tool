import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FeedbackTalkComponent } from './feedback-talk.component';

describe('FeedbackTalkComponent', () => {
  let component: FeedbackTalkComponent;
  let fixture: ComponentFixture<FeedbackTalkComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FeedbackTalkComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FeedbackTalkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
