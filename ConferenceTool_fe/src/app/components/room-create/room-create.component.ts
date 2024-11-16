import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {RoomService} from "../../services/room.service";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {ConferenceService} from "../../services/conference.service";
import {Conference} from "../../models/conference";
import {Room} from "../../models/room";
import {InputTextModule} from "primeng/inputtext";
import {MatButton} from "@angular/material/button";
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from "@angular/material/card";
import {NgIf} from "@angular/common";
import {PaginatorModule} from "primeng/paginator";
import {SidenavComponent} from "../sidenav/sidenav.component";
import {MatIcon} from "@angular/material/icon";
import {MatListItemIcon} from "@angular/material/list";
import {catchError} from "rxjs";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
    selector: 'app-room-create',
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
        MatIcon,
        MatListItemIcon
    ],
    templateUrl: './room-create.component.html',
    styleUrl: './room-create.component.css'
})
export class RoomCreateComponent implements OnInit {
    conferenceId: string | undefined;
    conference: Conference | undefined;
    room: Room | undefined;
    roomForm!: FormGroup;

    constructor(
        private route: ActivatedRoute,
        private roomService: RoomService,
        private conferenceService: ConferenceService,
        private fb: FormBuilder,
        private router: Router,
        private snackBar: MatSnackBar
    ) {
    }

    ngOnInit(): void {
        this.route.params.subscribe(params => {
            this.conferenceId = params['conferenceId'];
            if (this.conferenceId) {
                this.conferenceService.getConferenceById(this.conferenceId).subscribe((data) => {
                    this.conference = data;
                    this.initForm();
                });
            }
        });
    }


    initForm(): void {
        this.roomForm = this.fb.group({
            name: [null, Validators.required],
        });
    }

    createRoom() {
        if (this.roomForm.valid && this.conference) {
            const room = this.roomForm.value;
            room.conference_id = this.conference.conference_id;
            room.talks = [];
            console.log(room);
            this.roomService.createRoom(room).pipe(
                catchError((error) => {
                    console.error('Error creating room:', error);
                    const errorMessage = error && error.error ? error.error : 'Unknown error';
                    this.snackBar.open(errorMessage, 'Close', {duration: 5000});
                    throw error;
                })
            ).subscribe((data) => {
                console.log('Room created successfully.', data);
                this.router.navigate(['/conference-rooms/' + this.conferenceId]);
                this.snackBar.open('Room created', 'Close', {duration: 3000});
            });
        }


    }
}

