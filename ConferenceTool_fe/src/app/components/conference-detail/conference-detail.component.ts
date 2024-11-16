import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {SidenavComponent} from "../sidenav/sidenav.component";
import {Conference} from "../../models/conference";
import {Location} from "../../models/location";
import {ConferenceService} from "../../services/conference.service";
import {DatePipe, NgIf} from "@angular/common";
import {LocationService} from "../../services/location.service";
import {CalendarModule} from "primeng/calendar";
import {
    MatCard,
    MatCardActions,
    MatCardContent,
    MatCardHeader,
    MatCardSubtitle,
    MatCardTitle
} from "@angular/material/card";
import {InputTextModule} from "primeng/inputtext";
import {InputTextareaModule} from "primeng/inputtextarea";
import {MatButton} from "@angular/material/button";
import {DropdownModule} from "primeng/dropdown";
import {Router} from '@angular/router';


@Component({
    selector: 'app-conference-detail',
    standalone: true,
    styleUrls: ['./conference-detail.component.css'],
    templateUrl: './conference-detail.component.html',
    imports: [
        CalendarModule,
        ReactiveFormsModule,
        MatCardHeader,
        MatCard,
        MatCardContent,
        SidenavComponent,
        NgIf,
        InputTextModule,
        InputTextareaModule,
        MatButton,
        DatePipe,
        MatCardTitle,
        MatCardSubtitle,
        DropdownModule,
        MatCardActions
    ],
    providers: [DatePipe]
})
//TODO romove location from component
export class ConferenceDetailComponent implements OnInit {
    conferenceId: string | undefined;
    conference: Conference | undefined;
    location: Location | undefined;
    conferenceUpdated: Conference = {} as Conference;
    locations: Location[] = [];

    //Form props
    start_date: string | null | undefined;
    end_date: string | null | undefined;
    formName: string | undefined;
    formDescription: string | undefined;
    formLocation: string | undefined;

    locationOptions: any[] = [];
    conferenceForm: FormGroup = new FormGroup({});


    constructor(
        private route: ActivatedRoute,
        private conferenceService: ConferenceService,
        private locationService: LocationService,
        private datePipe: DatePipe,
        private fb: FormBuilder,
        private router: Router,
    ) {
    }

    ngOnInit(): void {
        this.route.params.subscribe(params => {
            //Retrieve conference ID from URL
            this.conferenceId = params['id'];

            if (this.conferenceId) {
                //Retrieve conference by ID
                this.conferenceService.getConferenceById(this.conferenceId).subscribe(data => {
                    this.conference = data;

                    if (this.conference) {
                        //Format dates
                        this.start_date = this.datePipe.transform(new Date(this.conference.start_date), 'MM/dd/yyyy HH:mm');
                        this.end_date = this.datePipe.transform(new Date(this.conference.end_date), 'MM/dd/yyyy HH:mm');
                        this.formName = this.conference.name;
                        this.formDescription = this.conference.description;
                        //Retrieve current location
                        this.locationService.getLocationById(this.conference.location_id).subscribe(data => {
                            this.location = data;

                            //Retrieve all possible locations
                            this.locationService.getAllLocations().subscribe(data => {
                                this.locations = data;
                                this.locationOptions = this.locations.map(loc => ({
                                    label: `${loc.street} ${loc.number}, ${loc.postal_code} ${loc.city}, ${loc.country}`,
                                    value: loc.location_id
                                }));

                                //Initialize form
                                this.formLocation = this.locationOptions.find(option => option.value === this.conference?.location_id);
                                this.conferenceForm = this.fb.group({
                                    name: [this.formName, [Validators.required, Validators.minLength(3)]],
                                    description: [this.formDescription, [Validators.required, Validators.minLength(10)]],
                                    start_date: [this.start_date, Validators.required],
                                    end_date: [this.end_date, Validators.required],
                                    location: [this.formLocation, Validators.required]
                                });
                            });

                        });


                    } else {
                        console.log('No conference found');
                        alert('No conference found');
                    }
                });

            } else {
                console.log('No conference ID found');
                alert('No conference ID found');
            }
        });
    }


    updateConference() {
        if (this.conferenceForm.valid && this.conference) {
            const formValues = this.conferenceForm.value;
            console.log('Form values:', formValues.location.value);

            this.conferenceUpdated.conference_id = this.conference.conference_id;
            this.conferenceUpdated.name = formValues.name;
            this.conferenceUpdated.description = formValues.description;
            this.conferenceUpdated.start_date = this.datePipe.transform(formValues.start_date, 'yyyy-MM-ddTHH:mm:ss.SSSSSS')!;
            this.conferenceUpdated.end_date = this.datePipe.transform(formValues.end_date, 'yyyy-MM-ddTHH:mm:ss.SSSSSS')!;
            this.conferenceUpdated.location_id = this.conference.location_id;

            console.log('Conference updated:', this.conferenceUpdated);
            try {
                this.conferenceService.updateConference(this.conferenceUpdated).subscribe(data => {
                    this.conference = data;
                });
                location.reload()
                //TODO: change to material alert
                alert('Conference updated successfully');
            } catch (e) {
                console.log('Error updating conference:', e);
                alert('Error updating conference');
            }
        } else {
            alert('Form is invalid, please try again.');
        }
    }

    goToLocationUpdatePage() {
        if (this.conference) {
            this.router.navigate(['/location-update/', this.conference.conference_id]);
        } else {
            console.log('No conference found');
            alert('No conference found');
        }
    }


}