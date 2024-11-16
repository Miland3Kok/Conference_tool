import {Component, OnInit} from '@angular/core';
import {SidenavComponent} from "../sidenav/sidenav.component";
import {ConferenceService} from "../../services/conference.service";
import {Conference} from "../../models/conference";
import {
    MatCell,
    MatCellDef,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderCellDef, MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef,
    MatTable,
    MatTableDataSource
} from "@angular/material/table";
import {DatePipe, NgIf} from "@angular/common";
import {MatButtonModule} from "@angular/material/button";
import {Router} from "@angular/router";
import {MatIcon} from "@angular/material/icon";
import {MatListItemIcon} from "@angular/material/list";
import {MatSnackBar, MatSnackBarModule} from '@angular/material/snack-bar';


@Component({
    selector: 'app-conferences',
    standalone: true,
    imports: [
        SidenavComponent,
        MatTable,
        MatColumnDef,
        MatHeaderCell,
        MatCell,
        MatCellDef,
        MatHeaderCellDef,
        MatHeaderRow,
        MatRow,
        MatHeaderRowDef,
        MatRowDef,
        DatePipe,

        MatButtonModule,
        NgIf,
        MatIcon,
        MatListItemIcon,
        MatSnackBarModule,
    ],
    templateUrl: './conferences.component.html',
    styleUrl: './conferences.component.css'
})
export class ConferencesComponent implements OnInit{
    conferences: MatTableDataSource<Conference> = new MatTableDataSource<Conference>([]);
    displayedColumns: string[] = ['blink', 'name', 'start_date', 'end_date', 'rooms', 'actions'];

    constructor(private conferenceService: ConferenceService, private router: Router,  private snackBar: MatSnackBar){
    }

    ngOnInit(): void {
        this.conferenceService.getAllConferences().subscribe(data => {
            this.conferences.data = data;
        });
    }

    toggleConferenceStatus(conference: Conference): void {
        try {
            this.conferenceService.toggleConferenceStatus(conference.conference_id).subscribe(data => {
                conference = data;
                let snackBarRef = this.snackBar.open('Conference status toggled successfully', 'Close', {
                    duration: 1000,
                });
                snackBarRef.afterDismissed().subscribe(() => {
                    location.reload();
                });
            });
        } catch (e) {
            let snackBarRef = this.snackBar.open('Error toggling conference status', 'Close', {
                duration: 1000,
            });
            console.error(e);
        }
    }

    goToDetailPage(conference: Conference) {
        this.router.navigate(['conference-detail/' + conference.conference_id]);
    }

    goToCreatePage() {
        this.router.navigate(['conference-create']).then(() => {
            this.conferenceService.getAllConferences().subscribe(data => {
                this.conferences.data = data;
            });
        });    }

    goToConferenceRoomsPage(conference: Conference) {
        this.router.navigate(['conference-rooms/' + conference.conference_id]);
    }
}
