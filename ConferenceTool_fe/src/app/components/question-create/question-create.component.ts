import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {Talk} from '../../models/talk';
import {QuestionService} from '../../services/question.service';
import {TalkService} from '../../services/talk.service';
import {SidenavComponent} from "../sidenav/sidenav.component";
import {MatStep, MatStepLabel, MatStepper, MatStepperPrevious} from "@angular/material/stepper";
import {MatCard, MatCardContent, MatCardHeader, MatCardSubtitle, MatCardTitle} from "@angular/material/card";
import {DropdownModule} from "primeng/dropdown";
import {NgForOf, NgIf} from "@angular/common";
import {MatIcon} from "@angular/material/icon";
import {MatButton, MatIconButton} from "@angular/material/button";
import {InputTextModule} from "primeng/inputtext";
import {MatError} from "@angular/material/form-field";
import {ClosedQuestion, MultipleChoiceQuestion, OpenQuestion} from "../../models/question";
import {CheckboxModule} from "primeng/checkbox";
import {ButtonModule} from "primeng/button";
import {RippleModule} from "primeng/ripple";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
    selector: 'app-question-create',
    templateUrl: './question-create.component.html',
    standalone: true,
    imports: [
        SidenavComponent,
        MatStepper,
        MatStep,
        MatCard,
        MatCardHeader,
        MatCardContent,
        ReactiveFormsModule,
        DropdownModule,
        NgIf,
        MatIcon,
        MatStepLabel,
        MatButton,
        InputTextModule,
        MatStepperPrevious,
        NgForOf,
        MatIconButton,
        MatCardTitle,
        MatCardSubtitle,
        MatError,
        CheckboxModule,
        ButtonModule,
        RippleModule
    ],
    styleUrls: ['./question-create.component.css']
})
export class QuestionCreateComponent implements OnInit {
    talkId: string | null = null;
    talk: Talk | undefined;
    typeFormGroup!: FormGroup;
    types: string[] | undefined;
    selectedType: string | undefined;
    questionFormGroup!: FormGroup;


    constructor(
        private route: ActivatedRoute,
        private questionService: QuestionService,
        private talkService: TalkService,
        private fb: FormBuilder,
        private router: Router,
        private snackBar: MatSnackBar
    ) {
    }

    ngOnInit() {
        this.route.params.subscribe(params => {
            this.talkId = params['talkId'];
            if (this.talkId) {
                this.talkService.getTalkById(this.talkId).subscribe(data => {
                    this.talk = data;
                    this.initTypeForm();
                    this.types = ['Closed', 'Multiple choice', 'Open'];
                });
            }
        });
    }

    initTypeForm() {
        this.typeFormGroup = this.fb.group({
            type: ['', Validators.required]
        });
    }

    setSelectedType() {
        this.selectedType = this.typeFormGroup.get('type')?.value;
        this.initQuestionForm();
    }

    initQuestionForm(): void {
        switch (this.selectedType) {
            case 'Closed':
                this.questionFormGroup = this.fb.group({
                    closedContent: ['', Validators.required],
                    closedCorrect: ['', Validators.required],
                    closedWrong: ['', Validators.required]
                });
                break;
            case 'Multiple choice':
                this.questionFormGroup = this.fb.group({
                    multipleContent: ['', Validators.required],
                    options: this.fb.array([this.createOption()]),
                });
                break;
            case 'Open':
                this.questionFormGroup = this.fb.group({
                    openContent: ['', Validators.required]
                });
                break;
            default:
                break;
        }
    }

    createOption(): FormGroup {
        return this.fb.group({
            option: ['', Validators.required],
            isCorrect: [false]
        });
    }

    addOption(): void {
        this.options.push(this.fb.group({
            option: ['', Validators.required],
            isCorrect: [false]
        }));
    }


    get options() {
        return this.questionFormGroup.get('options') as FormArray;
    }

    removeOption(index: number): void {
        this.options.removeAt(index);
    }

    createMultipleChoiceQuestion() {
        if (this.questionFormGroup.valid && this.talkId) {
            const options = this.questionFormGroup.get('options')?.value;

            const question: MultipleChoiceQuestion = {
                talkId: this.talkId,
                content: this.questionFormGroup.get('multipleContent')?.value,
                options: options.map((option: { option: string, isCorrect: boolean }) => {
                    return { optionText: option.option, isCorrect: option.isCorrect || false };
                }),
            };

            console.log("Multiple choice question: ", question);
            try {
                this.questionService.createMultipleChoiceQuestion(question).subscribe(data => {
                    console.log(data);
                });

                this.snackBar.open('Question created successfully', 'Close', {
                    duration: 2000,
                });
                setTimeout(() => {
                    this.router.navigate(['/question-talk/' + this.talkId]);
                }, 2100);

            } catch (e) {
                console.log(e);
            }
        }
    }


    createClosedQuestion() {
        if (this.questionFormGroup.valid && this.talkId) {

            let question: ClosedQuestion = {
                content: this.questionFormGroup.get('closedContent')?.value,
                talkId: this.talkId,
                correctAnswer: this.questionFormGroup.get('closedCorrect')?.value,
                falseAnswer: this.questionFormGroup.get('closedWrong')?.value,
            }

            console.log("Closed question: ", question);
            try {
                this.questionService.createClosedQuestion(question).subscribe(data => {
                    console.log(data);
                });
                this.snackBar.open('Question created successfully', 'Close', {
                    duration: 2000,
                });
                setTimeout(() => {
                    this.router.navigate(['/question-talk/' + this.talkId]);
                }, 2100);
            } catch (e) {
                console.log(e);
            }
        }
    }

    createOpenQuestion() {
        if (this.questionFormGroup.valid && this.talkId) {

            let question: OpenQuestion = {
                content: this.questionFormGroup.get('openContent')?.value,
                talkId: this.talkId
            }

            console.log("Open question: ", question);
            try {
                this.questionService.createOpenQuestion(question).subscribe(data => {
                    console.log(data);
                });
                this.snackBar.open('Question created successfully', 'Close', {
                    duration: 2000,
                });
                setTimeout(() => {
                    this.router.navigate(['/question-talk/' + this.talkId]);
                }, 2100);
            } catch (e) {
                console.log(e);
            }
        }
    }


    markFormGroupTouched(formGroup: FormGroup): void {
        (Object as any).values(formGroup.controls).forEach((control: any) => {
            control.markAsTouched();

            if (control instanceof FormGroup) {
                this.markFormGroupTouched(control);
            }
        });
    }


}
