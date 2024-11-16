import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuestionTalkTableComponent } from './question-talk-table.component';

describe('QuestionTalkTableComponent', () => {
  let component: QuestionTalkTableComponent;
  let fixture: ComponentFixture<QuestionTalkTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [QuestionTalkTableComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(QuestionTalkTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
