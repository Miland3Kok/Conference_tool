import {Component, OnInit} from '@angular/core';
import {SidenavComponent} from "../sidenav/sidenav.component";
import {Talk} from "../../models/talk";
import {QuestionService} from "../../services/question.service";
import {ClosedQuestion, Question} from "../../models/question";
import {NgForOf, NgIf} from "@angular/common";
import {MatFormField} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatRadioButton, MatRadioGroup} from "@angular/material/radio";
import {MatButton} from "@angular/material/button";
import {ActivatedRoute, Router} from "@angular/router";
import {RadioButtonModule} from "primeng/radiobutton";
import {ButtonModule} from "primeng/button";
import {UserToken} from "../../security-config/userToken.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-answer-question',
  standalone: true,
  imports: [
    SidenavComponent,
    NgForOf,
    NgIf,
    MatFormField,
    MatInput,
    ReactiveFormsModule,
    MatRadioGroup,
    MatRadioButton,
    MatButton,
    RadioButtonModule,
    ButtonModule
  ],
  templateUrl: './answer-question.component.html',
  styleUrl: './answer-question.component.css'
})
export class AnswerQuestionComponent implements OnInit {
  questions: Question[] = [];
  answerForm: FormGroup[] = [];
  answered: boolean[] = [];
  userId: string = '';

  constructor(private formBuilder: FormBuilder,
              private questionService: QuestionService,
              private route: ActivatedRoute,
              private snackBar: MatSnackBar,
              userToken: UserToken) {
    this.initUserId(userToken);
  }

  async initUserId(UserToken: UserToken) {
    this.userId = await UserToken.getUserId();
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const talkId = params['id'];
      if (talkId) {
        this.questionService.getQuestionsByTalk(talkId).subscribe((questions) => {
          this.questions = questions;
          this.questions.forEach((question, index) => {
            let group: any = {};
            switch (question.type) {
              case 'Open':
              case 'Closed':
                group = { answer: ['', Validators.required] };
                break;
              case 'MultipleChoice':
                group = { answer: this.formBuilder.array([]) };
                this.questionService.getOptionsByQuestionId(question.id).subscribe((options) => {
                  question.options = options;
                  options.forEach(() => {
                    (group.answer as FormArray).push(this.formBuilder.control(false));
                  });
                });
                break;
            }
            this.answerForm.push(this.formBuilder.group(group));
            this.answered.push(false);
          });
        });
      }
    });
  }

  onSubmit(index: number) {
    if (this.answerForm[index].valid) {
      let answerData: any;
      switch (this.questions[index].type) {
        case 'Open':
        case 'Closed':
          answerData = {
            userId: this.userId,
            questionId: this.questions[index].id,
            answer: this.answerForm[index].get('answer')?.value
          };
          break;
        case 'MultipleChoice':
          const selectedOptions = this.answerForm[index].get('answer')?.value
              .map((selected: boolean, optionIndex: number) => selected ? this.questions[index].options[optionIndex].optionText : null)
              .filter((option: string | null) => option !== null);
          answerData = {
            userId: this.userId,
            questionId: this.questions[index].id,
            answer: selectedOptions.join(', ')
          };
          break;
      }

      this.questionService.createAnswer(answerData).subscribe(response => {
        this.snackBar.open('Answer submitted successfully!', '', { duration: 2000 });
        this.answered[index] = true;
      }, error => {
        console.error('Error creating answer:', error);
      });
    }
  }
}


