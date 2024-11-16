import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuestionResultsComponent } from './question-results.component';

describe('QuestionResultsComponent', () => {
  let component: QuestionResultsComponent;
  let fixture: ComponentFixture<QuestionResultsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [QuestionResultsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(QuestionResultsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
