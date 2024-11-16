import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {FormArray, FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {QuestionService} from "../../services/question.service";
import {SidenavComponent} from "../sidenav/sidenav.component";
import {MatCard, MatCardContent, MatCardHeader, MatCardSubtitle, MatCardTitle} from "@angular/material/card";
import {NgForOf, NgIf} from "@angular/common";
import {MatStepLabel} from "@angular/material/stepper";
import {CheckboxModule} from "primeng/checkbox";
import {MatButton} from "@angular/material/button";
import {InputTextModule} from "primeng/inputtext";
import {ButtonModule} from "primeng/button";
import {RippleModule} from "primeng/ripple";

@Component({
    selector: 'app-question-update',
    templateUrl: './question-update.component.html',
    standalone: true,
    imports: [
        SidenavComponent,
        MatCard,
        MatCardHeader,
        MatCardContent,
        ReactiveFormsModule,
        NgIf,
        MatStepLabel,
        CheckboxModule,
        MatButton,
        InputTextModule,
        NgForOf,
        MatCardTitle,
        MatCardSubtitle,
        ButtonModule,
        RippleModule
    ],
    styleUrls: ['./question-update.component.css']
})
export class QuestionUpdateComponent implements OnInit {
    questionId: string | null | undefined;
    question: any;
    questionFormGroup!: FormGroup;

    constructor(
        private route: ActivatedRoute,
        private fb: FormBuilder,
        private questionService: QuestionService
    ) {
    }



    ngOnInit(): void {
        this.route.params.subscribe(params => {
            this.questionId = params['id'];
            if (this.questionId) {
                this.questionService.getQuestionById(this.questionId).subscribe((data) => {
                    this.question = data;
                    this.initQuestionForm();

                });
            }
        });
    }

    initQuestionForm(): void {
        if (this.question) {
            switch (this.question.type) {
                case 'CLOSED':
                    this.questionFormGroup = this.fb.group({
                        closedContent: [this.question.content, Validators.required],
                        closedCorrect: [this.question.correctAnswer, Validators.required],
                        closedWrong: [this.question.falseAnswer, Validators.required]
                    });
                    break;


                case 'MULTIPLE_CHOICE':
                    this.createMultipleChoiceForm();
                    break;
                case 'OPEN':
                    this.questionFormGroup = this.fb.group({
                        openContent: [this.question.content, Validators.required]
                    });
                    break;
                default:
                    break;
            }
        }
    }

    addOption(): void {
        this.options.push(new FormGroup({
            option: new FormControl(),
            isCorrect: new FormControl()
        }));
    }

    get options() {
        return this.questionFormGroup.get('options') as FormArray;
    }

    createMultipleChoiceForm(): void {
        this.questionFormGroup = this.fb.group({
            multipleContent: [this.question.content, Validators.required],
            options: this.fb.array([]),
        });

        const optionsArray = this.questionFormGroup.get('options') as FormArray;

        this.question.options.forEach((option: any) => {
            console.log(option);
            optionsArray.push(this.fb.group({
                option: [option.optionText, Validators.required],
                isCorrect: [option.correct || false]
            }));
        });

    }


    updateClosedQuestion(): void {
        if (this.questionFormGroup.valid && this.questionId) {
            let question: any = {
                id: this.questionId,
                content: this.questionFormGroup.get('closedContent')?.value,
                correctAnswer: this.questionFormGroup.get('closedCorrect')?.value,
                falseAnswer: this.questionFormGroup.get('closedWrong')?.value,
                talkId: this.question.talkId,
            };
            this.questionService.updateClosedQuestion(question).subscribe(data => {
                console.log(data);
            });
        }
    }

    updateMultipleChoiceQuestion(): void {
        if (this.questionFormGroup.valid && this.questionId) {
            const options = this.questionFormGroup.get('options')?.value;
            const question: any = {
                id: this.questionId,
                content: this.questionFormGroup.get('multipleContent')?.value,
                options: options.map((option: any) => {
                    return {optionText: option.option, isCorrect: option.isCorrect || false};
                })
            };

            console.log(question);

            this.questionService.updateMultipleChoiceQuestion(question).subscribe(data => {
                console.log(data);
            });


        }
    }

    updateOpenQuestion(): void {
        if (this.questionFormGroup.valid && this.questionId) {
            const question: any = {
                id: this.questionId,
                content: this.questionFormGroup.get('openContent')?.value
            };
            this.questionService.updateOpenQuestion(question).subscribe(data => {
                console.log(data);
            });
        }
    }


    removeOption(index: number): void {
        this.options.removeAt(index);
    }
}
