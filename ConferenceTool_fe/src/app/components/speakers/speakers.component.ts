import {Component, OnInit} from '@angular/core';
import {SidenavComponent} from "../sidenav/sidenav.component";
import {Speaker} from "../../models/speaker";
import {SpeakerService} from "../../services/speaker.service";
import { MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";
import {NgForOf, NgIf} from "@angular/common";
import {CardModule} from "primeng/card";
import {ButtonModule} from "primeng/button";
import {MatIcon} from "@angular/material/icon";
import {Conference} from "../../models/conference";
import {ConferenceService} from "../../services/conference.service";
import {UserService} from "../../services/user.service";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'app-speakers',
  standalone: true,
    imports: [
        SidenavComponent,
        MatCardModule, MatButtonModule, NgIf, NgForOf, CardModule, ButtonModule, MatIcon

    ],
  templateUrl: './speakers.component.html',
  styleUrl: './speakers.component.css'
})
export class SpeakersComponent implements OnInit {
    speakers: Speaker[] | undefined;
    conference: Conference | undefined;


    constructor( private speakerService : SpeakerService, private conferenceService : ConferenceService, private userService: UserService, private sanitizer: DomSanitizer) { }

    ngOnInit(): void {
      this.speakerService.getSpeakersFromActiveConference().subscribe((dataSpeaker) => {
        this.speakers = dataSpeaker;
          this.speakers.forEach(speaker => {
              this.userService.getProfilePictureOfUser(speaker.user.user_id.toString()).subscribe(blob => {
                  let objectURL = URL.createObjectURL(blob);
                  speaker.user.givenProfilePicture = this.sanitizer.bypassSecurityTrustUrl(objectURL);
              });
          });


        console.log(this.speakers);
          this.conferenceService.getActiveConference().subscribe(dataConference => {
              this.conference = dataConference;
          });
      });
    }

    protected readonly Range = Range;
}
