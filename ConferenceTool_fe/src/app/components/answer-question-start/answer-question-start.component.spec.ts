import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnswerQuestionStartComponent } from './answer-question-start.component';

describe('AnswerQuestionStartComponent', () => {
  let component: AnswerQuestionStartComponent;
  let fixture: ComponentFixture<AnswerQuestionStartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnswerQuestionStartComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AnswerQuestionStartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
