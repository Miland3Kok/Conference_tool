import {Component, OnInit} from '@angular/core';
import {Talk} from "../../models/talk";
import {TalkService} from "../../services/talk.service";
import {Speaker} from "../../models/speaker";
import {UserToken} from "../../security-config/userToken.service";
import {SpeakerService} from "../../services/speaker.service";
import {UserService} from "../../services/user.service";
import {DatePipe, NgForOf} from "@angular/common";
import {User} from "../../models/user";
import {SidenavComponent} from "../sidenav/sidenav.component";
import {
    MatCell,
    MatCellDef,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderCellDef,
    MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef,
    MatTable, MatTableDataSource
} from "@angular/material/table";
import {MatButton} from "@angular/material/button";
import {RoomService} from "../../services/room.service";
import {Router} from '@angular/router';


@Component({
  selector: 'app-talks-speaker',
  standalone: true,
    imports: [
        NgForOf,
        SidenavComponent,
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
        DatePipe
    ],
  templateUrl: './talks-speaker.component.html',
  styleUrl: './talks-speaker.component.css'
})
export class TalksSpeakerComponent {
    token: string | undefined;
    speaker: Speaker | undefined;
    talks: Talk[] = [];
    displayedColumns: string[] = ['title', 'description', 'start_date', 'end_date', 'room', 'actions'];
    user: User | undefined = undefined;
    speakerId: string = '';
    userId: string = '';

    constructor(private talkService: TalkService, userToken: UserToken, private roomService: RoomService, private speakerService: SpeakerService
    , private router: Router) {
        this.initUserId(userToken);
    }

    async initUserId(UserToken: UserToken) {
        this.userId = await UserToken.getUserId();
        console.log(this.userId);
        this.getTalksBySpeakerId();
    }

    getTalksBySpeakerId() {
        if (this.userId) {
            this.speakerService.getSpeakerIdByUserId(this.userId).subscribe(speakerId => {
                this.speakerId = speakerId.toString();
                this.talkService.getTalksBySpeakerId(this.speakerId).subscribe(talks => {
                    talks.forEach(talk => {
                        this.roomService.getRoomById(talk.room).subscribe(room => {
                            talk.room = room.name;
                        });
                    });
                    this.talks = talks;
                });
            });
        }
    }

    navigateToQuestionTalk(talkId: string) {
        console.log("talkId: ", talkId);
        this.router.navigate(['/question-talk', talkId]);
    }
}
