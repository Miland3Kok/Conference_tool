import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {LoginComponent} from "./components/login/login.component";
import {MyAccountComponent} from "./components/my-account/my-account.component";
import {FloorplanComponent} from "./components/floorplan/floorplan.component";
import {SpeakersComponent} from "./components/speakers/speakers.component";
import {PlanningComponent} from "./components/planning/planning.component";
import {NotificationsComponent} from "./components/notifications/notifications.component";
import {NgModule} from "@angular/core";
import {ConferencesComponent} from "./components/conferences/conferences.component";
import {ConferenceDetailComponent} from "./components/conference-detail/conference-detail.component";
import {canActivate} from "./security-config/auth.guard.service";
import {UserToken} from "./security-config/userToken.service";
import {ConferenceCreateComponent} from "./components/conference-create/conference-create.component";
import {MessagesComponent} from "./components/messages/messages.component";
import {MessageDetailComponent} from "./components/message-detail/message-detail.component";
import {CreateMessageComponent} from "./components/create-message/create-message.component";
import {CreateUserComponent} from "./components/create-user/create-user.component";
import {FeedbackTalkComponent} from "./components/feedback-talk/feedback-talk.component";
import {UserTableComponent} from "./components/user-table/user-table.component";
import {ScheduleComponent} from "./components/schedule/schedule.component";
import {ConferenceRoomsComponent} from "./components/conference-rooms/conference-rooms.component";
import {RoomUpdateComponent} from "./components/room-update/room-update.component";
import {TalkUpdateComponent} from "./components/talk-update/talk-update.component";
import {TalkCreateComponent} from "./components/talk-create/talk-create.component";
import {RoomCreateComponent} from "./components/room-create/room-create.component";
import {LocationUpdateComponent} from "./components/location-update/location-update.component";
import {TalksSpeakerComponent} from "./components/talks-speaker/talks-speaker.component";
import {QuestionTalkTableComponent} from "./components/question-talk-table/question-talk-table.component";
import {QuestionCreateComponent} from "./components/question-create/question-create.component";
import {QuestionUpdateComponent} from "./components/question-update/question-update.component";
import {AnswerQuestionComponent} from "./components/answer-question/answer-question.component";
import {QuestionResultsComponent} from "./components/question-results/question-results.component";

export const routes: Routes = [
    {path: '', component: HomeComponent},
    {path: 'login', component: LoginComponent},
    {path: 'home', component: HomeComponent, canActivate: [canActivate]},
    {path: 'myAccount', component: MyAccountComponent, canActivate: [canActivate]},
    {path: 'notifications', component: NotificationsComponent, canActivate: [canActivate]},
    {path: 'planning', component: PlanningComponent, canActivate: [canActivate]},
    {path: 'schedule', component: ScheduleComponent, canActivate: [canActivate]},
    {path: 'talk-feedback', component: FeedbackTalkComponent, canActivate: [canActivate]},
    {path: 'speakers', component: SpeakersComponent, canActivate: [canActivate]},
    {
        path: 'floorplan',
        component: FloorplanComponent,
        canActivate: [canActivate],
        data: {requiredRole: ['user', 'admin', 'organizer', 'speaker']}
    },
    {
        path: 'conferences', component: ConferencesComponent, canActivate: [canActivate],
        data: {requiredRole: ['admin', 'organizer']}
    },
    {
        path: 'conference-detail/:id', component: ConferenceDetailComponent, canActivate: [canActivate],
        data: {requiredRole: ['admin', 'organizer']}
    },
    {
        path: 'conference-create', component: ConferenceCreateComponent,
        data: {requiredRole: ['admin', 'organizer']}
    },
    {
        path: 'conference-rooms/:id', component: ConferenceRoomsComponent, canActivate: [canActivate],
        data: {requiredRole: ['admin', 'organizer']}
    },
    {
        path: 'location-update/:conferenceId', component: LocationUpdateComponent, canActivate: [canActivate],
        data: {requiredRole: ['admin', 'organizer']}
    },
    {
        path: 'messages', component: MessagesComponent, canActivate: [canActivate],
        data: {requiredRole: ['organizer', 'admin']}
    },
    {
        path: 'new-message', component: CreateMessageComponent, canActivate: [canActivate],
        data: {requiredRole: 'speaker'}
    },
    {
        path: 'message-detail/:id', component: MessageDetailComponent, canActivate: [canActivate],
        data: {requiredRole: ['organizer', 'admin']}
    },
    {
        path: 'create-user', component: CreateUserComponent, canActivate: [canActivate],
        data: {requiredRole: ['organizer', 'admin']}
    },
    {
        path: 'user-table', component: UserTableComponent, canActivate: [canActivate],
        data: {requiredRole: ['organizer', 'admin']}
    },
    {
        path: 'room-update/:id', component: RoomUpdateComponent, canActivate: [canActivate],
        data: {requiredRole: ['admin', 'organizer']}
    },
    {
        path: 'room-create/:conferenceId', component: RoomCreateComponent, canActivate: [canActivate],
        data: {requiredRole: ['admin', 'organizer']}
    },
    {
        path: 'talk-update/:id', component: TalkUpdateComponent, canActivate: [canActivate],
        data: {requiredRole: ['admin', 'organizer']}
    },
    {
        path: 'talk-create/:roomId', component: TalkCreateComponent, canActivate: [canActivate],
        data: {requiredRole: ['admin', 'organizer']}
    },
    {
        path: 'speaker-talks', component: TalksSpeakerComponent, canActivate: [canActivate],
        data: {requiredRole: ['speaker']}
    },
    {
        path: 'question-talk/:id', component: QuestionTalkTableComponent, canActivate: [canActivate],
        data: {requiredRole: ['speaker']}
    },
    {
        path: 'question-create/:talkId', component: QuestionCreateComponent, canActivate: [canActivate],
        data: {requiredRole: ['speaker']}
    },
    {
        path: 'question-update/:id', component: QuestionUpdateComponent, canActivate: [canActivate],
        data: {requiredRole: ['speaker']}
    },
    {
        path: 'answer-question/:id', component: AnswerQuestionComponent, canActivate: [canActivate],
        data: {requiredRole: ['speaker', 'user', 'admin', 'organizer']}
    },
    {
        path: 'question-results/:id', component: QuestionResultsComponent, canActivate: [canActivate],
        data: {requiredRole: ['speaker', 'user', 'admin', 'organizer']}
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload'})],
    exports: [RouterModule],
    providers: [Permissions, UserToken]
})

export class AppRoutingModule {
}
