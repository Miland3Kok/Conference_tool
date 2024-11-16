import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Conference} from '../../models/conference';
import {Room} from '../../models/room';
import {Talk} from '../../models/talk';
import {TalkService} from '../../services/talk.service';
import {ConferenceService} from '../../services/conference.service';
import {SidenavComponent} from "../sidenav/sidenav.component";
import {MatTab, MatTabGroup} from "@angular/material/tabs";
import {NgClass, NgForOf, NgIf, NgStyle} from "@angular/common";
import {MatIcon} from "@angular/material/icon";
import {TalkDetailComponent} from "../talk-detail/talk-detail.component";
import {MatDialog} from "@angular/material/dialog";
import {ScheduleModule} from "./schedule.module";
import {MessagesModule} from "primeng/messages";
import {ButtonModule} from "primeng/button";
import {ChipModule} from "primeng/chip";
import {MatChip} from "@angular/material/chips";
import {UserToken} from "../../security-config/userToken.service";
import {MatButton} from "@angular/material/button";
import {TruncatePipe} from "../../pipes/truncate.pipe";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Subject} from "rxjs";

@Component({
    selector: 'app-schedule',
    templateUrl: './schedule.component.html',
    styleUrls: ['./schedule.component.css'],
    imports: [
        SidenavComponent,
        MatTabGroup,
        MatTab,
        NgForOf,
        NgStyle,
        MatIcon,
        ScheduleModule,
        MessagesModule,
        ButtonModule,
        ChipModule,
        MatChip,
        NgClass,
        MatButton,
        NgIf,
        TruncatePipe
    ],
    standalone: true,
})

export class ScheduleComponent implements OnInit {
    conference: Conference | null = null;
    rooms: Room[] = [];
    talks: Talk[] = [];
    schedule: any[] = [];
    dates: Date[] = [];
    favouriteTalks: Talk[] = [];
    userId: string = '';

    timeSlots: string[] = [
        'time-0800', 'time-0815', 'time-0830', 'time-0845', 'time-0900', 'time-0915', 'time-0930', 'time-0945',
        'time-1000', 'time-1015', 'time-1030', 'time-1045', 'time-1100', 'time-1115', 'time-1130', 'time-1145',
        'time-1200', 'time-1215', 'time-1230', 'time-1245', 'time-1300', 'time-1315', 'time-1330', 'time-1345',
        'time-1400', 'time-1415', 'time-1430', 'time-1445', 'time-1500', 'time-1515', 'time-1530', 'time-1545',
        'time-1600', 'time-1615', 'time-1630', 'time-1645', 'time-1700', 'time-1715', 'time-1730', 'time-1745',
        'time-1800'
    ];

    eveningTimeSlots: string[] = [
      'time-1800', 'time-1815', 'time-1830', 'time-1845', 'time-1900', 'time-1915', 'time-1930', 'time-1945', 'time-2000', 'time-2015', 'time-2030', 'time-2045',
        'time-2100', 'time-2115', 'time-2130', 'time-2145', 'time-2200', 'time-2215', 'time-2230', 'time-2245',
        'time-2300', 'time-2315', 'time-2330', 'time-2345'
    ]

    predefinedColors = ['#1d242b', '#163e3c', '#45474d', '#003256', '#FF33A8'];
    favouriteColor: string = '#e9531d'

    constructor(private talkService: TalkService,
                private conferenceService: ConferenceService,
                private dialog: MatDialog,
                private userToken: UserToken,
                private snackBar: MatSnackBar,
                private changeDetector: ChangeDetectorRef) {
        this.initUserId();
    }

    async initUserId() {
        this.userId = await this.userToken.getUserId();
    }

    ngOnInit(): void {
        this.conferenceService.getActiveConference().subscribe((data) => {
            this.conference = data;
            if (this.conference) {
                this.conferenceService.getRoomsForConference(this.conference.conference_id).subscribe((rooms) => {
                    this.rooms = rooms;
                    if (this.rooms && this.conference) {
                        this.talkService.getFavouriteTalks(this.userId, this.conference.conference_id.toString()).subscribe((talks) => {
                            this.favouriteTalks = talks;
                            console.log(this.favouriteTalks)
                        });
                        this.conferenceService.getTalksForConference(this.conference.conference_id).subscribe((talks) => {
                            this.talks = talks;
                            this.generateDates();
                        });
                    }
                });
            }
        });
    }

    isFavouriteTalk(talkId: string): boolean {
        return this.favouriteTalks.some(talk => talk.talk_id && talk.talk_id.toString() === talkId);
    }

    getRoomColor(roomId: string, talkId: string): string {
        if (this.isFavouriteTalk(talkId)) {
            return this.favouriteColor;
        }
        const index = this.rooms.findIndex((room) => room.room_id === roomId);
        const colorIndex = index % this.predefinedColors.length;
        return this.predefinedColors[colorIndex];
    }

    onFavouriteButtonClick(talkId: string): void {
        if (this.isFavouriteTalk(talkId)) {
            this.talkService.unfavouriteTalk(this.userId, talkId).subscribe(() => {
                this.favouriteTalks = this.favouriteTalks.filter(talk => talk.talk_id.toString() !== talkId);
            });
        } else {
            this.talkService.favouriteTalk(this.userId, talkId).then(() => {
                this.favouriteTalks = [...this.favouriteTalks, this.talks.filter(talk => talk.talk_id.toString() === talkId)[0]];
            });
        }
    }

    generateDates(): void {
        if (!this.conference) return;
        const startDate = new Date(this.conference.start_date);
        const endDate = new Date(this.conference.end_date);

        for (let date = startDate; date <= endDate; date.setDate(date.getDate() + 1)) {
            this.dates.push(new Date(date));
        }

        console.log(this.dates);
    }

    getHoursFromDate(date: string): string | number {
        const formatted = new Date(date);
        return formatted.getHours() < 10 ? '0' + formatted.getHours() : formatted.getHours();
    }

    getMinutesFromDate(start_date: string): string | number {
        const formatted = new Date(start_date);
        return formatted.getMinutes() < 10 ? '0' + formatted.getMinutes() : formatted.getMinutes();
    }

    getTrackNumberForRoom(roomId: string, date: Date): number {
        return this.getRoomsForDate(date).findIndex((room) => room.room_id === roomId);
    }

    getRoomName(room: string): string | undefined {
        return this.rooms.find((r) => r.room_id === room)?.name;
    }

    getTimeFromSlot(timeSlot: string): string {
        const hours = timeSlot.slice(5, 7);
        const minutes = timeSlot.slice(7);
        return `${hours}:${minutes}`;
    }

    getRoomsForDate(date: Date): Room[] {
        return this.rooms.filter((room) => {
            return (
                this.talks.find((talk) => {
                    const talkStartDate = new Date(talk.start_date);
                    return (
                        talkStartDate.getDate() === date.getDate() &&
                        talkStartDate.getMonth() === date.getMonth() &&
                        talkStartDate.getFullYear() === date.getFullYear() &&
                        talk.room === room.room_id
                    );
                }) !== undefined
            );
        });
    }

    getTalksForDate(date: Date): Talk[] {
        return this.talks.filter((talk) => {
            const talkStart = new Date(talk.start_date);
            return (
                talkStart.getDate() === date.getDate() &&
                talkStart.getMonth() === date.getMonth() &&
                talkStart.getFullYear() === date.getFullYear()
            );
        });
    }

    getTalkDuration(talk: Talk): number {
        const startDate = new Date(talk.start_date);
        const endDate = new Date(talk.end_date);
        return (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60);
    }

    openDialog(talk: Talk): void {
        this.dialog.open(TalkDetailComponent, {
            data: {
                talk: talk
            }
        });
    }
}
