import {Component, OnInit} from '@angular/core';
import {SidenavComponent} from "../sidenav/sidenav.component";
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from "@angular/material/card";
import {InputTextModule} from "primeng/inputtext";
import {ReactiveFormsModule} from "@angular/forms";
import {Talk} from "../../models/talk";
import {TalkService} from "../../services/talk.service";
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";
import {NgIf} from "@angular/common";
import {QuestionService} from "../../services/question.service";
import {Question} from "../../models/question";
import {
    MatCell,
    MatCellDef,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderCellDef,
    MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef,
    MatTable
} from "@angular/material/table";
import {MatButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {MatListItemIcon} from "@angular/material/list";
import {filter, Subscription} from "rxjs";

@Component({
    selector: 'app-question-talk-table',
    standalone: true,
    imports: [
        SidenavComponent,
        MatCardHeader,
        MatCardTitle,
        MatCard,
        InputTextModule,
        MatCardContent,
        ReactiveFormsModule,
        NgIf,
        MatTable,
        MatColumnDef,
        MatHeaderCell,
        MatHeaderCellDef,
        MatCell,
        MatCellDef,
        MatButton,
        MatHeaderRow,
        MatHeaderRowDef,
        MatRow,
        MatRowDef,
        MatIcon,
        MatListItemIcon
    ],
    templateUrl: './question-talk-table.component.html',
    styleUrl: './question-talk-table.component.css'
})
export class QuestionTalkTableComponent implements OnInit {
    talk: Talk | undefined;
    questions: Question[] = [];


    constructor(private talkService: TalkService,
                private route: ActivatedRoute,
                private questionService: QuestionService,
                private router: Router) {
    }

    ngOnInit(): void {
        this.route.params.subscribe(params => {
            this.talkService.getTalkById(params['id']).subscribe((data) => {
                this.talk = data;
                if (this.talk) {
                    this.route.params.subscribe(params => {
                        this.questionService.getQuestionsByTalk(this.talk?.talk_id.toString()).subscribe((data) => {
                            console.log(data);
                            this.questions = data;
                        })
                    })
                }
            });
        });
    }

    goToCreateQuestionPage() {
        this.router.navigate(['question-create/', this.talk?.talk_id]);
    }


    deleteQuestion(element: Question) {
        if (element.id) {
            try {
                this.questionService.deleteQuestion(element.id).subscribe((data) => {
                    console.log(data)
                });
                alert('Question deleted successfully');
                location.reload();
            } catch (e) {
                console.log(e);
                alert('Error deleting question');
            }
        }
    }

    goToUpdateQuestionPage(element: Question) {
        this.router.navigate(['question-update/', element.id]);
    }

    goToResultsPage(element: Question) {
        this.router.navigate(['question-results/', element.id]);
    }
}
