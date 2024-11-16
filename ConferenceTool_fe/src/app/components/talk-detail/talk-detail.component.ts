import {Component, Inject, OnInit} from '@angular/core';
import {Talk} from "../../models/talk";
import {TalkService} from "../../services/talk.service";
import {DatePipe, NgForOf, NgIf, NgStyle} from "@angular/common";
import {MatCard, MatCardContent, MatCardHeader, MatCardModule} from "@angular/material/card";
import {MatDivider} from "@angular/material/divider";
import {RoomService} from "../../services/room.service";
import {Room} from "../../models/room";
import {SidenavComponent} from "../sidenav/sidenav.component";
import {MatChip, MatChipAvatar, MatChipListbox} from "@angular/material/chips";
import {MatList, MatListItem} from "@angular/material/list";
import {MatLine} from "@angular/material/core";
import {MatButton} from "@angular/material/button";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {FeedbackTalkComponent} from "../feedback-talk/feedback-talk.component";
import {FeedbackService} from "../../services/feedback.service";
import {MatIcon} from "@angular/material/icon";
import {UserToken} from "../../security-config/userToken.service";
import {Router} from "@angular/router";
import {QuestionService} from "../../services/question.service";

@Component({
  selector: 'app-talk-detail',
  standalone: true,
    imports: [
        NgIf,
        DatePipe,
        MatCard,
        MatCardHeader,
        MatCardContent,
        MatCardModule,
        MatDivider,
        SidenavComponent,
        MatChip,
        NgForOf,
        MatListItem,
        MatList,
        MatLine,
        MatChipAvatar,
        MatButton,
        MatChipListbox,
        FeedbackTalkComponent,
        MatIcon,
        NgStyle
    ],
  templateUrl: './talk-detail.component.html',
  styleUrl: './talk-detail.component.css'
})
export class TalkDetailComponent implements OnInit{
  talk: Talk;
  room: Room | null = null;
  rating: number = 0;
  userHasGivenFeedback: boolean = false;
  userId: string = '';
  doesTalkHaveQuestions: boolean = false;


    constructor(private roomService: RoomService,
                @Inject(MAT_DIALOG_DATA) public data: {talk: Talk},
                private feedbackService: FeedbackService,
                private questionService: QuestionService,
                userToken: UserToken,
                private router: Router) {
      this.talk = data.talk;
        this.initUserId(userToken);
  }

    async initUserId(userToken: UserToken) {
        this.userId = await userToken.getUserId();
    }

  ngOnInit(): void {
        if (this.talk)
          this.roomService.getRoomById(this.talk.room)
              .subscribe(data => this.room = data);

      this.feedbackService.userHasGivenFeedback(this.talk.talk_id, this.userId)
          .subscribe(data => {
              this.userHasGivenFeedback = data;
          });

      this.feedbackService.getRatingOfTalk(this.talk.talk_id)
          .subscribe(data => {
                this.rating = data;
          });

        this.questionService.doesTalkHaveQuestions(this.talk.talk_id.toString()).subscribe(data => {
            this.doesTalkHaveQuestions = data;
        })
  }

    shareOnLinkedIn(talk: any) {
        const url = encodeURIComponent('http://localhost:4200/talk-detail/' + talk.id);
        const text = encodeURIComponent(`Check out this talk: ${talk.title}`);
        const shareUrl = `https://www.linkedin.com/sharing/share-offsite/?url=${url}&title=${text}`;
        window.open(shareUrl, '_blank');
    }

    shareOnFacebook(talk: any) {
        const url = encodeURIComponent('http://localhost:4200/talk-detail/' + talk.id);
        const shareUrl = `https://www.facebook.com/sharer/sharer.php?u=${url}`;
        window.open(shareUrl, '_blank');
    }

    onClickQuestion(talkId: number) {
        this.router.navigate(['/answer-question', talkId]);
    }
}
