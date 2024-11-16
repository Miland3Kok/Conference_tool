import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Room} from "../../models/room";
import {RoomService} from "../../services/room.service";
import {SidenavComponent} from "../sidenav/sidenav.component";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {InputTextModule} from "primeng/inputtext";
import {ButtonModule} from "primeng/button";
import {MatCard, MatCardActions, MatCardContent, MatCardHeader, MatCardTitle} from "@angular/material/card";
import {DatePipe, NgIf} from "@angular/common";
import {MatButton} from "@angular/material/button";
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
import {Talk} from "../../models/talk";
import {MatIcon} from "@angular/material/icon";
import {MatListItemIcon} from "@angular/material/list";
import {TalkService} from "../../services/talk.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {catchError} from "rxjs";

@Component({
    selector: 'app-room-update',
    standalone: true,
    imports: [
        SidenavComponent,
        ReactiveFormsModule,
        InputTextModule,
        ButtonModule,
        MatCard,
        NgIf,
        MatCardContent,
        MatCardHeader,
        MatCardTitle,
        MatCardActions,
        MatButton,
        MatTable,
        MatCell,
        MatCellDef,
        MatColumnDef,
        MatHeaderCell,
        MatHeaderRow,
        MatHeaderRowDef,
        MatRow,
        MatRowDef,
        MatHeaderCellDef,
        MatIcon,
        MatListItemIcon,

    ],

    templateUrl: './room-update.component.html',
    styleUrl: './room-update.component.css',
    providers: [DatePipe]
})
export class RoomUpdateComponent implements OnInit {
    roomId: string | undefined;
    room: Room | undefined;
    roomForm!: FormGroup;


    constructor(
        private route: ActivatedRoute,
        private roomService: RoomService,
        private talkService: TalkService,
        private fb: FormBuilder,
        public datePipe: DatePipe,
        private router: Router,
        private snackBar: MatSnackBar
    ) {
    }

    ngOnInit(): void {
        this.route.params.subscribe(params => {
            this.roomId = params['id'];
            if (this.roomId) {
                this.roomService.getRoomById(this.roomId).subscribe((data) => {
                    this.room = data;
                    this.initForm();
                });
            }
        });
    }

    initForm(): void {
        if (this.room) {
            this.roomForm = this.fb.group({
                name: [this.room?.name, Validators.required],
            });
        } else {
            //TODO
        }
    }

    updateRoom() {
        if (this.roomForm.valid && this.room) {
            const updatedRoom = {
                room_id: this.room.room_id,
                name: this.roomForm.value.name,
                conference_id: this.room.conference_id,
                talks: []
            };
            this.roomService.updateRoom(updatedRoom).pipe(
                catchError((error) => {
                    console.error('Error updating room:', error);
                    const errorMessage = error && error.error ? error.error : 'Unknown error';
                    this.snackBar.open(errorMessage, 'Close', {duration: 5000});
                    throw error;
                })
            ).subscribe((data) => {
                console.log('Room updated successfully.', data);
                this.snackBar.open('Room updated', 'Close', {duration: 3000});
            });
        } else {
            console.log('Form is invalid');
        }
    }

    goToUpdateTalkPage(talk: Talk) {
        this.router.navigate(['talk-update/', talk.talk_id]);
    }

    goToCreateTalkPage() {
        this.router.navigate(['talk-create/', this.roomId]);
    }

    removeTalkFromRoom(talk: Talk) {
        if (talk.talk_id) {
            this.talkService.deleteTalk(talk.talk_id.toString()).pipe(
                catchError((error) => {
                    console.error('Error removing talk from room:', error);
                    const errorMessage = error && error.error ? error.error : 'Unknown error';
                    this.snackBar.open(errorMessage, 'Close', {duration: 5000});
                    throw error;
                })
            ).subscribe(() => {
                this.snackBar.open('Talk removed', 'Close', {duration: 3000});
               location.reload();
            });
        } else {
            alert('Error removing talk from room');
        }
    }
}

