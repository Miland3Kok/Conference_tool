import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {LocationService} from "../../services/location.service";
import {ActivatedRoute} from "@angular/router";
import {ConferenceService} from "../../services/conference.service";
import {Conference} from "../../models/conference";
import {Location} from "../../models/location";
import {SidenavComponent} from "../sidenav/sidenav.component";
import {MatCard, MatCardContent, MatCardHeader, MatCardSubtitle, MatCardTitle} from "@angular/material/card";
import {InputTextModule} from "primeng/inputtext";
import {MatButton} from "@angular/material/button";
import {NgIf} from "@angular/common";

@Component({
    selector: 'app-location-update',
    standalone: true,
    imports: [
        SidenavComponent,
        MatCard,
        MatCardHeader,
        MatCardContent,
        ReactiveFormsModule,
        InputTextModule,
        MatButton,
        MatCardTitle,
        MatCardSubtitle,
        NgIf
    ],
    templateUrl: './location-update.component.html',
    styleUrl: './location-update.component.css'
})
export class LocationUpdateComponent implements OnInit {
    locationForm!: FormGroup;
    conferenceId: string | null | undefined;
    location: Location | undefined;
    conference: Conference | undefined;

    constructor(
        private fb: FormBuilder,
        private locationService: LocationService,
        private conferenceService: ConferenceService,
        private route: ActivatedRoute,
    ) {
    }

    ngOnInit(): void {
        this.route.params.subscribe(params => {
            this.conferenceId = params['conferenceId'];
            this.conferenceService.getConferenceById(this.conferenceId!).subscribe((conference) => {
                this.conference = conference;
                if (this.conference) {
                    this.locationService.getLocationById(this.conference.location_id).subscribe((location) => {
                        this.location = location;
                        this.initForm();
                    });
                }
            });

        });
    }


    private initForm() {
        if (this.location) {
            this.locationForm = this.fb.group({
                street: [this.location.street, Validators.required],
                number: [this.location.number, Validators.required],
                city: [this.location.city, Validators.required],
                postal_code: [this.location.postal_code, Validators.required],
                country: [this.location.country, Validators.required],
                extraInfo: [this.location.extraInfo]
            });
        }
    }

    updateLocation() {
        if (this.locationForm.valid && this.location) {
            this.location = {
                location_id: this.location.location_id,
                street: this.locationForm.value.street,
                number: this.locationForm.value.number,
                city: this.locationForm.value.city,
                postal_code: this.locationForm.value.postal_code,
                country: this.locationForm.value.country,
                extraInfo: this.locationForm.value.extraInfo
            }
            try {

                this.locationService.updateLocation(this.location).subscribe(() => {
                    console.log('Location updated');
                    alert('Location updated successfully');
                    location.reload();
                });

            } catch (e) {
                alert('Error updating location')
                console.log(e);
            }

        }


    }
}
