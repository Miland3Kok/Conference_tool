import {Component, OnInit} from '@angular/core';
import {SidenavComponent} from "../sidenav/sidenav.component";
import {
  MatCard,
  MatCardActions,
  MatCardContent,
  MatCardHeader,
  MatCardSubtitle,
  MatCardTitle
} from "@angular/material/card";
import {FlexLayoutModule} from "@angular/flex-layout";
import {MatButton} from "@angular/material/button";
import {NgForOf} from "@angular/common";
import {Conference} from "../../models/conference";
import {ConferenceService} from "../../services/conference.service";
import {DatePipe} from "@angular/common";
import {UpcomingConferenceComponent} from "../upcoming-conference/upcoming-conference.component";
import {UserToken} from "../../security-config/userToken.service";
import {Talk} from "../../models/talk";
import {TalkService} from "../../services/talk.service";
import { CarouselModule } from 'primeng/carousel';
import {TagModule} from "primeng/tag";
import {ButtonModule} from "primeng/button";
import {MatChip} from "@angular/material/chips";
import {Room} from "../../models/room";
import {RoomService} from "../../services/room.service";
import {MatSidenavModule} from "@angular/material/sidenav";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    SidenavComponent,
    MatCard,
    MatCardHeader,
    MatCardContent,
    FlexLayoutModule,
    MatCardActions,
    MatButton,
    MatCardTitle,
    MatCardSubtitle,
    NgForOf,
    MatSidenavModule,

    DatePipe, UpcomingConferenceComponent, CarouselModule, TagModule, ButtonModule, MatChip
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  providers: [DatePipe]
})
export class HomeComponent implements OnInit{
  conference: Conference | null = null;
  userId: string ='';
  talks: Talk[] = [];
  responsiveOptions: any[] | undefined;
  rooms: Room[] = [];

  constructor(private conferenceService: ConferenceService, private talkService: TalkService, private roomService: RoomService, UserToken: UserToken) {
    this.initUserId(UserToken);
  }

  async initUserId(UserToken: UserToken) {
    this.userId = await UserToken.getUserId();
  }

  ngOnInit(): void {
    this.conferenceService.getActiveConference().subscribe(data => {
      this.conference = data;
      this.roomService.getRoomsByConferenceId(this.conference.conference_id.toString()).subscribe(data => {
        this.rooms = data;
      });
      if (this.conference)
        this.talkService.getFeaturedTalks(this.conference.conference_id.toString()).subscribe(data =>
            this.talks = data
        );


    });

    this.responsiveOptions = [
      {
        breakpoint: '1024px',
        numVisible: 2,
        numScroll: 2
      },
      {
        breakpoint: '768px',
        numVisible: 1,
        numScroll: 1
      },
      {
        breakpoint: '560px',
        numVisible: 1,
        numScroll: 1
      }
    ];
  }

  getRoomName(roomId: string): string {
    const room = this.rooms.find(room => room.room_id === roomId);
    return room ? room.name : 'No room assigned';
  }

}
