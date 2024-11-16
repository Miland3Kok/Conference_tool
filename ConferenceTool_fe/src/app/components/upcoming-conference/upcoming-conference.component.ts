import {Component, Input, OnInit} from '@angular/core';
import {Conference} from "../../models/conference";
import {MatDivider} from "@angular/material/divider";
import {DatePipe} from "@angular/common";
import {ConferenceService} from "../../services/conference.service";

@Component({
  selector: 'app-upcoming-conference',
  standalone: true,
    imports: [
        MatDivider,
        DatePipe
    ],
  templateUrl: './upcoming-conference.component.html',
  styleUrl: './upcoming-conference.component.css'
})
export class UpcomingConferenceComponent implements OnInit{
  @Input() conference: Conference | null = null;

    constructor(private datePipe: DatePipe){
    }

    ngOnInit() {
        if (this.conference && this.conference.start_date) {
            const [day, month, year, time] = this.conference.start_date.split('-');
            if (time) {
                const [hour, minute, second] = time.split(':');
                this.conference.start_date = new Date(Number(year), Number(month) - 1, Number(day), Number(hour), Number(minute), Number(second)).toString();
            }
        }
    }
}
