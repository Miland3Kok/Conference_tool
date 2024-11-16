import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {Talk} from "../../models/talk";
import {ActivatedRoute, Router} from "@angular/router";
import {DatePipe, NgIf} from "@angular/common";
import {TalkService} from "../../services/talk.service";
import {InputTextModule} from "primeng/inputtext";
import {MatButton} from "@angular/material/button";
import {MatCard, MatCardActions, MatCardContent, MatCardHeader, MatCardTitle} from "@angular/material/card";
import {PaginatorModule} from "primeng/paginator";
import {SidenavComponent} from "../sidenav/sidenav.component";
import {InputTextareaModule} from "primeng/inputtextarea";
import {CalendarModule} from "primeng/calendar";
import {MatError} from "@angular/material/form-field";
import {
    MatCell,
    MatCellDef,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderCellDef,
    MatHeaderRow,
    MatHeaderRowDef,
    MatRow,
    MatRowDef,
    MatTable
} from "@angular/material/table";
import {Speaker} from "../../models/speaker";
import {SpeakerService} from "../../services/speaker.service";
import {MatIcon} from "@angular/material/icon";
import {MatListItemIcon} from "@angular/material/list";
import {MatSnackBar} from "@angular/material/snack-bar";
import {catchError} from "rxjs";

@Component({
    selector: 'app-talk-update',
    standalone: true,
    imports: [
        InputTextModule,
        MatButton,
        MatCard,
        MatCardContent,
        MatCardHeader,
        MatCardTitle,
        NgIf,
        PaginatorModule,
        ReactiveFormsModule,
        SidenavComponent,
        InputTextareaModule,
        CalendarModule,
        MatError,
        MatCell,
        MatCellDef,
        MatColumnDef,
        MatHeaderCell,
        MatHeaderRow,
        MatHeaderRowDef,
        MatRow,
        MatRowDef,
        MatTable,
        MatHeaderCellDef,
        MatCardActions,
        MatIcon,
        MatListItemIcon
    ],
    templateUrl: './talk-update.component.html',
    styleUrl: './talk-update.component.css',
    providers: [DatePipe]
})
export class TalkUpdateComponent implements OnInit {
    talkId: string | undefined;
    talk: Talk | undefined;
    talkForm!: FormGroup;
    speakerForm!: FormGroup;
    start_date: string | null | undefined;
    end_date: string | null | undefined;
    possibleSpeakers!: Speaker[];
    speakerOptions: any[] = [];


    constructor(
        private route: ActivatedRoute,
        private talkService: TalkService,
        private fb: FormBuilder,
        public datePipe: DatePipe,
        private router: Router,
        private speakerService: SpeakerService,
        private snackBar: MatSnackBar
    ) {
    }

    ngOnInit(): void {
        this.route.params.subscribe(params => {
            this.talkId = params['id'];
            this.talkService.getTalkById(this.talkId!).subscribe((data) => {
                this.talk = data;
                this.initForm();

                this.speakerService.getAllSpeakers().subscribe((data) => {
                    this.possibleSpeakers = data;
                    this.initSpeakerOptions();

                    console.log(this.possibleSpeakers);
                });

            });
            this.initSpeakerForm();

        });
    }

    private initForm() {
        if (this.talk) {
            this.start_date = this.datePipe.transform(new Date(this.talk.start_date), 'MM/dd/yyyy HH:mm');
            this.end_date = this.datePipe.transform(new Date(this.talk.end_date), 'MM/dd/yyyy HH:mm');

            this.talkForm = this.fb.group({
                title: [this.talk.title, Validators.required],
                description: [this.talk.description, Validators.required],
                start_date: [this.start_date, Validators.required],
                end_date: [this.end_date, Validators.required],
            });
        } else {
            //TODO
        }
    }

    private initSpeakerForm() {
        this.speakerForm = this.fb.group({
            speaker: [null, Validators.required]
        });
    }

    private initSpeakerOptions() {
        const filteredSpeakers = this.possibleSpeakers.filter(speaker => {
            return !this.talk?.speakers.some(talkSpeaker => talkSpeaker.phone === speaker.phone);
        });

        this.speakerOptions = filteredSpeakers.map(speaker => {
            return {
                label: speaker.user.firstName + ' ' + speaker.user.lastName,
                value: speaker.speaker_id
            };
        });
    }


    updateTalk() {
        if (this.talkForm.valid && this.talk) {
            this.talk = {
                "talk_id": this.talk.talk_id,
                "title": this.talkForm.value.title,
                "description": this.talkForm.value.description,
                "start_date": this.datePipe.transform(this.talkForm.value.start_date, 'yyyy-MM-ddTHH:mm:ss.SSSSSS')!,
                "end_date": this.datePipe.transform(this.talkForm.value.end_date, 'yyyy-MM-ddTHH:mm:ss.SSSSSS')!,
                "room": this.talk.room,
                "speakers": []
            };
            this.talkService.updateTalk(this.talk).pipe(
                catchError(error => {
                    console.error('An error occurred while updating the talk:', error);
                    const errorMessage = error && error.error ? error.error : 'Unknown error';
                    this.snackBar.open(errorMessage, 'Close', {duration: 5000});
                    throw error;
                })
            ).subscribe(data => {
                console.log(data);
                this.snackBar.open('Talk updated successfully', 'Close', {duration: 3000});
            });
        }
    }

    removeSpeakerFromTalk(speaker: Speaker) {
        console.log(speaker.speaker_id);
        console.log(this.talkId);
        if (this.talkId && speaker.speaker_id) {
            this.talkService.removeSpeakerFromTalk(this.talkId, speaker.speaker_id.toString()).pipe(
                catchError(error => {
                    console.error('An error occurred while removing speaker from talk:', error);
                    const errorMessage = error && error.error ? error.error : 'Unknown error';
                    this.snackBar.open(errorMessage, 'Close', {duration: 5000});
                    throw error;
                })
            ).subscribe(data => {
                console.log(data);
                this.snackBar.open('Speaker removed from talk successfully', 'Close', {duration: 3000});
                location.reload();
            });
        }
    }

    addSpeakerToTalk() {
        if (this.speakerForm.valid && this.talkId) {
            this.talkService.addSpeakerToTalk(this.talkId, this.speakerForm.value.speaker.value).pipe(
                catchError(error => {
                    console.error('An error occurred while adding speaker to talk:', error);
                    const errorMessage = error && error.error ? error.error : 'Unknown error';
                    this.snackBar.open(errorMessage, 'Close', {duration: 5000});
                    throw error;
                })
            ).subscribe(data => {
                console.log(data);
                this.snackBar.open('Speaker added to talk successfully', 'Close', {duration: 3000});
                location.reload();
            });
        }
    }


    goToCreateSpeakerPage() {
        this.router.navigate(['/create-user']);
    }


}
