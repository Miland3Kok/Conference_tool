import {Component, OnInit} from '@angular/core';
import {SidenavComponent} from "../sidenav/sidenav.component";
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef, MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef,
  MatTable,
  MatTableDataSource
} from "@angular/material/table";
import {NgIf} from "@angular/common";
import {MessagesService} from "../../services/messages.service";
import {MessageObject} from "../../models/message";
import {Router} from "@angular/router";
import {SpeakerService} from "../../services/speaker.service";

@Component({
  selector: 'app-messages',
  standalone: true,
  imports: [
    SidenavComponent,
    MatCell,
    MatCellDef,
    MatColumnDef,
    MatHeaderCell,
    NgIf,
    MatHeaderCellDef,
    MatTable,
    MatHeaderRow,
    MatHeaderRowDef,
    MatRow,
    MatRowDef
  ],
  templateUrl: './messages.component.html',
  styleUrl: './messages.component.css'
})
export class MessagesComponent implements OnInit {
  messages: MatTableDataSource<MessageObject> = new MatTableDataSource<MessageObject>([]);
  displayedColumns: string[] = ['blink', 'subject', 'speaker'];
  speakerName: string = '';

  constructor(private messagesService: MessagesService, private speakerService: SpeakerService, private router: Router) { }

  ngOnInit(): void {
    this.messagesService.getAllMessagesForOrganisation().subscribe(data => {
      this.messages.data = data;
    });
  }

  openMessage(message: MessageObject) {
    this.router.navigate(['message-detail/' + message.message_id]);
  }
}
