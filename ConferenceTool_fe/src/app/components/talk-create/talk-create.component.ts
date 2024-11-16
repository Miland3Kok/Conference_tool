import {Component, OnInit} from '@angular/core';
import {SidenavComponent} from "../sidenav/sidenav.component";
import {MatCard, MatCardContent, MatCardHeader, MatCardSubtitle, MatCardTitle} from "@angular/material/card";
import {
    AbstractControl,
    FormBuilder,
    FormGroup,
    ReactiveFormsModule,
    ValidationErrors,
    ValidatorFn,
    Validators
} from "@angular/forms";
import {InputTextModule} from "primeng/inputtext";
import {InputTextareaModule} from "primeng/inputtextarea";
import {CalendarModule} from "primeng/calendar";
import {MatButton} from "@angular/material/button";
import {ActivatedRoute, Router} from "@angular/router";
import {TalkService} from "../../services/talk.service";
import {DatePipe, NgIf} from "@angular/common";
import {Talk} from "../../models/talk";
import {MatError} from "@angular/material/form-field";
import {Room} from "../../models/room";
import {RoomService} from "../../services/room.service";
import {MatIcon} from "@angular/material/icon";
import {MatListItemIcon} from "@angular/material/list";
import {MatSnackBar} from "@angular/material/snack-bar";
import {catchError} from "rxjs";

@Component({
    selector: 'app-talk-create',
    standalone: true,
    imports: [
        SidenavComponent,
        MatCard,
        MatCardHeader,
        MatCardContent,
        ReactiveFormsModule,
        InputTextModule,
        InputTextareaModule,
        CalendarModule,
        MatButton,
        MatError,
        NgIf,
        MatCardTitle,
        MatCardSubtitle,
        MatIcon,
        MatListItemIcon,
    ],
    templateUrl: './talk-create.component.html',
    styleUrl: './talk-create.component.css',
    providers: [DatePipe]

})
export class TalkCreateComponent implements OnInit {
    roomId: string | undefined;
    room: Room | undefined;
    talkForm!: FormGroup;
    start_date: string | null | undefined;
    end_date: string | null | undefined;
    talk: Talk | undefined;

    constructor(
        private route: ActivatedRoute,
        private talkService: TalkService,
        private roomService: RoomService,
        private fb: FormBuilder,
        public datePipe: DatePipe,
        private router: Router,
        private snackBar: MatSnackBar
    ) {
    }


    ngOnInit(): void {
        this.route.params.subscribe(params => {
            this.roomId = params['roomId'];
            if (this.roomId) {
                this.roomService.getRoomById(this.roomId).subscribe(room => {
                    this.room = room;
                });
            }
            this.initForm();
        });
    }

    private initForm() {
        this.start_date = this.datePipe.transform(new Date(Date.now()), 'MM/dd/yyyy HH:mm');
        this.end_date = this.datePipe.transform(new Date(Date.now()), 'MM/dd/yyyy HH:mm');

        this.talkForm = this.fb.group({
            title: [null, Validators.required],
            description: [null, Validators.required],
            start_date: [this.start_date, Validators.required],
            end_date: [this.end_date, Validators.required],
        });

    }

    todayOrLaterValidator(): ValidatorFn {
        return (control: AbstractControl): ValidationErrors | null => {
            const selectedDate = control.value;
            const today = new Date();

            if (selectedDate && selectedDate < today) {
                return {todayOrLater: true};
            }

            return null;
        };
    }

    endDateAfterStartDateValidator(): ValidatorFn {
        return (control: AbstractControl): ValidationErrors | null => {
            const startDate = this.talkForm.get('start_date')?.value as Date;
            const endDate = control.value as Date;

            console.log('Start date:', startDate);
            console.log('End date:', endDate);

            if (startDate && endDate && endDate <= startDate) {
                return {endDateBeforeStartDate: true};
            }

            return null;
        };
    }


    createTalk() {
        if (this.talkForm.valid) {
            const talk = {
                ...this.talkForm.value,
                start_date: this.datePipe.transform(this.talkForm.value.start_date, 'yyyy-MM-ddTHH:mm:ss.SSSSSS')!,
                end_date: this.datePipe.transform(this.talkForm.value.end_date, 'yyyy-MM-ddTHH:mm:ss.SSSSSS')!,
                room: this.roomId,
                speakers_id: []
            };
            this.talkService.createTalk(talk).pipe(
                catchError((error) => {
                    console.error('Error creating talk:', error);
                    const errorMessage = error && error.error ? error.error : 'Unknown error';
                    this.snackBar.open(errorMessage, 'Close', {duration: 5000});
                    throw error;
                })
            ).subscribe((data) => {
                console.log('Talk created successfully.', data);
                this.snackBar.open('Talk created successfully', 'Close', {duration: 3000});
                this.router.navigate(['room-update/', this.roomId]);
            });
        } else {
            console.log('Form is invalid');
        }
    }
}
