import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {SidenavComponent} from "../sidenav/sidenav.component";
import {MessagesService} from "../../services/messages.service";
import {MessageObject} from "../../models/message";
import {
    MatCard,
    MatCardContent,
    MatCardFooter,
    MatCardHeader,
    MatCardSubtitle,
    MatCardTitle
} from "@angular/material/card";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-message-detail',
  standalone: true,
    imports: [
        SidenavComponent,
        MatCard,
        MatCardContent,
        MatCardTitle,
        MatCardHeader,
        MatCardFooter,
        MatCardSubtitle,
        NgIf
    ],
  templateUrl: './message-detail.component.html',
  styleUrl: './message-detail.component.css'
})
export class MessageDetailComponent implements OnInit {
    message_id: string | undefined;
    message: MessageObject | undefined;

    constructor(private route: ActivatedRoute, private messagesService: MessagesService) { }

    ngOnInit(): void {
        this.route.params.subscribe(params => {
            this.message_id = params['id'];
        });
        this.messagesService.getMessageById(this.message_id!).subscribe(data => {
            this.message = data;
        });
    }
}
