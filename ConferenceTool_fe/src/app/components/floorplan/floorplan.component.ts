import {Component, OnInit} from '@angular/core';
import {SidenavComponent} from "../sidenav/sidenav.component";
import {MatButton} from "@angular/material/button";
import {UploadIcon} from "primeng/icons/upload";
import {ConferenceService} from "../../services/conference.service";
import {Conference} from "../../models/conference";
import {KeycloakService} from "keycloak-angular";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-floorplan',
  standalone: true,
    imports: [
        SidenavComponent,
        MatButton,
        UploadIcon,
        NgIf
    ],
  templateUrl: './floorplan.component.html',
  styleUrl: './floorplan.component.css'
})
export class FloorplanComponent implements OnInit{
    conference: null | Conference;
    floorplan: null | string;

    constructor(private conferenceService: ConferenceService, private keycloakService: KeycloakService) {
        this.conference = null;
        this.floorplan = null;

    }


    ngOnInit() {
        this.conferenceService.getActiveConference().subscribe({
            next: (conference) => {
                this.conference = conference;
                this.conferenceService.getFloorplan(this.conference.conference_id.toString()).subscribe({
                    next: (floorplanBlob) => {
                        this.floorplan = URL.createObjectURL(floorplanBlob);
                    },
                    error: (error) => {
                        console.error(error);
                    }
                });
            },
            error: (error) => {
                console.error(error);
            }
        });


    }

    hasRole(role: string): boolean {
        // Assuming you have a service that provides the current user's roles
        const userRoles = this.keycloakService.getUserRoles();
        return userRoles.includes(role);
    }

    uploadFloorPlan() {
        const fileInput = document.getElementById('imageUpload') as HTMLInputElement;
        if (fileInput.files && fileInput.files[0]) {
            const file = fileInput.files[0];
            if (this.conference?.conference_id) {
                this.conferenceService.uploadFloorPlan(this.conference?.conference_id.toString(), file).subscribe({
                    next: (response) => {
                        console.log(response);
                    },
                    error: (error) => {
                        console.error(error);
                    }
                });
            }
        }
    }
}
