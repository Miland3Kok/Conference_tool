import {Component, OnInit} from '@angular/core';
import {UserAnswer} from "../../models/userAnswer";
import {ActivatedRoute} from "@angular/router";
import {QuestionService} from "../../services/question.service";
import {map} from "rxjs";
import {ChartModule} from "primeng/chart";
import {Option, Question} from "../../models/question";
import {NgForOf, NgIf} from "@angular/common";
import {TagCloudComponent} from "angular-tag-cloud-module";
import {CloudData} from "angular-tag-cloud-module";
import {SidenavComponent} from "../sidenav/sidenav.component";
import {randomInt} from "node:crypto";
import {MatCard, MatCardHeader, MatCardSubtitle, MatCardTitle} from "@angular/material/card";

@Component({
    selector: 'app-question-results',
    standalone: true,
    imports: [
        ChartModule,
        NgIf,
        NgForOf,
        TagCloudComponent,
        SidenavComponent,
        MatCard,
        MatCardHeader,
        MatCardTitle,
        MatCardSubtitle
    ],
    templateUrl: './question-results.component.html',
    styleUrl: './question-results.component.css'
})
export class QuestionResultsComponent implements OnInit {
    userAnswers: UserAnswer[] = [];
    question: Question | undefined;
    openAnswers: string[] = [];
    data: any;
    cloudData: CloudData[] = []; // Add this line
    labels: string[] = []; // Add this line


    constructor(private route: ActivatedRoute, private questionService: QuestionService) {
    }

    ngOnInit(): void {
        this.route.params.subscribe(params => {
            const questionId = params['id'];
            if (questionId) {
                this.questionService.getQuestionById(questionId).subscribe(question => {
                    this.question = question;
                    if (this.question?.type === "MULTIPLE_CHOICE") {
                        this.questionService.getOptionsByQuestionId(questionId).subscribe((options: Option[]) => {
                            this.labels = options.map(option => option.optionText); // Use options as labels
                            this.data = new Array(this.labels.length).fill(0); // Initialize data array with zeros
                        });
                    }
                });

                this.questionService.getAnswersByQuestionId(questionId).pipe(map(answers => answers as UserAnswer[])).subscribe((answers: UserAnswer[]) => {
                    this.userAnswers = answers;
                    if (this.question?.type === "MULTIPLE_CHOICE") {
                        this.updateMultipleChoiceChartData();
                    } else if (this.question?.type === "OPEN") {
                        this.openAnswers = this.userAnswers.map(answer => answer.answer);
                        this.prepareCloudData();
                    } else {
                        this.updateChartData();
                    }
                });
            }
        });
    }


    prepareCloudData(): void {
        let wordCounts = this.openAnswers.reduce((acc: {[key: string]: number}, word) => {
            acc[word] = (acc[word] || 0) + 1;
            return acc;
        }, {});

        this.cloudData = Object.entries(wordCounts).map(([text, weight]) => {
            return { text, weight: Number(weight) } as CloudData;
        });
        console.log('cloudData:', this.cloudData);
    }


    updateChartData(): void {
        let labels = ["yes", "no"];
        if (this.question?.type === "CLOSED") {
            labels = [this.question.correctAnswer, this.question.falseAnswer];
        }
        const correctCount = this.userAnswers.filter(answer => answer.answer === this.question?.correctAnswer).length;
        const falseCount = this.userAnswers.filter(answer => answer.answer === this.question?.falseAnswer).length;
        const data = [correctCount, falseCount];

        this.data = {
            labels: labels,
            options: labels,
            datasets: [
                {
                    data: data,
                    backgroundColor: [
                        "#36A2EB",
                        "#FF6384",
                        "#FFCE56",
                        "#4BC0C0",
                        "#9966CC",
                        "#FF9F40",
                        "#32CD32",
                        // add more colors if you have many answers
                    ],
                    hoverBackgroundColor: [
                        "#36A2EB",
                        "#FF6384",
                        "#FFCE56",
                        "#4BC0C0",
                        "#9966CC",
                        "#FF9F40",
                        "#32CD32",
                        // add more colors if you have many answers
                    ]
                }]
        };
    }

    updateMultipleChoiceChartData(): void {
        this.userAnswers.forEach(userAnswer => {
            const answerArray = userAnswer.answer.split(', ');
            answerArray.forEach(answer => {
                const index = this.labels.indexOf(answer);
                if (index !== -1) {
                    this.data[index]++;
                }
            });
        });

        this.data = {

            labels: this.labels,
            datasets: [
                {
                    label: 'Answers',
                    data: this.data,
                    backgroundColor: [
                        "#FF6384",
                        "#36A2EB",
                        "#FFCE56",
                        "#4BC0C0",
                        "#9966CC",
                        "#FF9F40",
                        "#32CD32",

                        // add more colors if you have many answers
                    ],
                    hoverBackgroundColor: [
                        "#FF6384",
                        "#36A2EB",
                        "#FFCE56",
                        "#4BC0C0",
                        "#9966CC",
                        "#FF9F40",
                        "#32CD32",
                        // add more colors if you have many answers
                    ]
                }
            ]
        };
    }
}
