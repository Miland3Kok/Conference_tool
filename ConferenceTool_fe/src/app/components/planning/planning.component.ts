import {Component, OnInit} from "@angular/core";
import {Conference} from "../../models/conference";
import {Talk} from "../../models/talk";
import {TalkService} from "../../services/talk.service";
import {ConferenceService} from "../../services/conference.service";
import {Room} from "../../models/room";
import {SidenavComponent} from "../sidenav/sidenav.component";
import {MatGridList, MatGridTile} from "@angular/material/grid-list";
import {DatePipe, NgForOf, NgIf} from "@angular/common";
import {MatTab, MatTabGroup} from "@angular/material/tabs";
import {MatCard, MatCardContent, MatCardTitle} from "@angular/material/card";
import {MatDialog} from "@angular/material/dialog";
import {TalkDetailComponent} from "../talk-detail/talk-detail.component";

@Component({
    selector: 'app-planning',
    templateUrl: './planning.component.html',
    standalone: true,
    imports: [
        SidenavComponent,
        MatGridList,
        MatGridTile,
        NgForOf,
        NgIf,
        MatTabGroup,
        MatTab,
        DatePipe,
        DatePipe,
        MatCard,
        MatCardTitle,
        MatCardContent
    ],
    styleUrls: ['./planning.component.css']
})
export class PlanningComponent implements OnInit {
    conference: Conference | null = null;
    rooms: Room[] = [];
    talks: Talk[] = [];
    schedule: any[] = [];
    dates: Date[] = [];


    constructor(private talkService: TalkService, private conferenceService: ConferenceService, private dialog: MatDialog) {

    }

    ngOnInit(): void {
        this.conferenceService.getActiveConference().subscribe(data => {
            this.conference = data;
            if (this.conference) {
                this.conferenceService.getRoomsForConference(this.conference.conference_id).subscribe(rooms => {
                    this.rooms = rooms;
                    if (this.conference)
                        this.conferenceService.getTalksForConference(this.conference.conference_id).subscribe(talks => {
                            this.talks = talks;
                            this.generateSchedule();
                            this.generateDates();
                        });
                });
            }
        });
    }

    openDialog(talk: Talk): void {
        this.dialog.open(TalkDetailComponent, {
            data: {
                talk: talk
            }
        });
    }

    generateDates(): void {
        if (this.conference === null) return;
        let startDate = new Date(this.conference.start_date);
        let endDate = new Date(this.conference.end_date);

        for (let date = startDate; date <= endDate; date.setDate(date.getDate() + 1)) {
            this.dates.push(new Date(date));
        }
    }


    generateSchedule(): void {
        for (let hour = 8; hour <= 18; hour++) {
            for (let minute = 0; minute < 60; minute += 15) {
                const timeSlot: { time: string, rooms: { room: Room, talks: Talk[] }[] } = { time: `${hour}:${minute < 10 ? '0' + minute : minute}`, rooms: [] };
                for (const room of this.rooms) {
                    const roomSchedule: { room: Room, talks: Talk[] } = { room: room, talks: [] };
                    for (const talk of this.talks) {
                        let talkStart = new Date(talk.start_date);
                        let talkEnd = new Date(talk.end_date);
                        let [timeSlotHour, timeSlotMinute] = timeSlot.time.split(':').map(Number);
                        let timeSlotStart = new Date(talkStart.getFullYear(), talkStart.getMonth(), talkStart.getDate(), timeSlotHour, timeSlotMinute);
                        let timeSlotEnd = new Date(talkStart.getFullYear(), talkStart.getMonth(), talkStart.getDate(), timeSlotHour, timeSlotMinute + 30); // assuming each timeslot is 30 minutes

                        if (talk.room === room.room_id && (talkStart.getTime() <= timeSlotStart.getTime() && talkEnd.getTime() >= timeSlotEnd.getTime())) {
                            roomSchedule.talks.push(talk);
                            console.log(`Added talk ${talk.title} to room ${room.room_id} at timeslot ${timeSlot.time}`);
                        }
                    }
                    timeSlot.rooms.push(roomSchedule);
                }
                this.schedule.push(timeSlot);
            }
        }
    }

    public isTalkInTimeSlot(talk: Talk, timeSlot: string): number | false {
        let talkStart: Date;
        let talkEnd: Date;
        if (Array.isArray(talk.start_date)) {
            talkStart = new Date(Number(talk.start_date[0]), Number(talk.start_date[1]) - 1, Number(talk.start_date[2]), Number(talk.start_date[3]), Number(talk.start_date[4]));
            talkEnd = new Date(Number(talk.end_date[0]), Number(talk.end_date[1]) - 1, Number(talk.end_date[2]), Number(talk.end_date[3]), Number(talk.end_date[4]));
        } else {
            talkStart = new Date(talk.start_date);
            talkEnd = new Date(talk.end_date);
        }

        let [timeSlotHour, timeSlotMinute] = timeSlot.split(':').map(Number);
        let timeSlotStart = new Date(talkStart.getFullYear(), talkStart.getMonth(), talkStart.getDate(), timeSlotHour, timeSlotMinute);
        let timeSlotEnd = new Date(talkStart.getFullYear(), talkStart.getMonth(), talkStart.getDate(), timeSlotHour, timeSlotMinute + 15); // assuming each timeslot is 15 minutes


        return false;
    }

    public getTalksForDate(date: Date): Talk[] {
        return this.talks.filter(talk => {
            let talkStart: Date;
            if (Array.isArray(talk.start_date)) {
                talkStart = new Date(talk.start_date[0], talk.start_date[1] - 1, talk.start_date[2], talk.start_date[3], talk.start_date[4]);
            } else {
                talkStart = new Date(talk.start_date);
            }
            return talkStart.getDate() === date.getDate() &&
                talkStart.getMonth() === date.getMonth() &&
                talkStart.getFullYear() === date.getFullYear();
        });
    }
}
