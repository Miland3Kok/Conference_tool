import {Component, OnInit} from '@angular/core';
import {SidenavComponent} from "../sidenav/sidenav.component";
import {Room} from "../../models/room";
import {Conference} from "../../models/conference";
import {Talk} from "../../models/talk";
import {ConferenceService} from "../../services/conference.service";
import {ActivatedRoute, Router} from "@angular/router";
import {
    MatCell,
    MatCellDef,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderCellDef,
    MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef,
    MatTable
} from "@angular/material/table";
import {MatButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {MatListItemIcon} from "@angular/material/list";
import {RoomService} from "../../services/room.service";
import {FormsModule} from "@angular/forms";

@Component({
    selector: 'app-conference-rooms',
    standalone: true,
    imports: [
        SidenavComponent,
        MatTable,
        MatColumnDef,
        MatHeaderCell,
        MatCell,
        MatHeaderCellDef,
        MatCellDef,
        MatButton,
        MatHeaderRow,
        MatHeaderRowDef,
        MatRowDef,
        MatRow,
        MatIcon,
        MatListItemIcon,
        FormsModule
    ],
    templateUrl: './conference-rooms.component.html',
    styleUrl: './conference-rooms.component.css',
    providers:  [ RoomService ]
})
export class ConferenceRoomsComponent implements OnInit {
    conferenceId: number | undefined;
    conference: Conference | null = null;
    talks: Talk[] = [];
    rooms: Room[] = [];



    constructor(
        private conferenceService: ConferenceService,
        private roomService: RoomService,
        private route: ActivatedRoute,
        private router: Router){
    }

    ngOnInit(): void {
        this.route.params.subscribe(params => {
            this.conferenceId = params['id'];
            this.conferenceService.getRoomsForConference(this.conferenceId!).subscribe((data) => {
                this.rooms = data;
                console.log(this.rooms);
            });
        });
    }

    goToUpdateRoomUPage(room: Room) {
        this.router.navigate(['room-update/' + room.room_id]);
    }

    goToCreateRoomPage() {
        this.router.navigate(['room-create/' + this.conferenceId]);
    }

    deleteRoom(room: Room) {
        try {
            this.roomService.deleteRoom(room.room_id).subscribe((data) => {
                console.log(data)
            });
            alert('Room deleted successfully');
            location.reload();
        } catch (e) {
            alert('Error deleting room');
            console.log(e);
        }


    }
}
