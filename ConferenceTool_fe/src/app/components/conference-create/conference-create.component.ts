import {Component, OnInit} from '@angular/core';
import {SidenavComponent} from "../sidenav/sidenav.component";
import {MatStep, MatStepLabel, MatStepper, MatStepperNext, MatStepperPrevious} from "@angular/material/stepper";
import {MatError, MatFormField} from "@angular/material/form-field";
import {MatButton} from "@angular/material/button";
import {
    AbstractControl,
    FormBuilder,
    FormGroup,
    ReactiveFormsModule,
    ValidationErrors,
    ValidatorFn,
    Validators
} from "@angular/forms";
import {MatInput} from "@angular/material/input";
import {MatCard, MatCardContent, MatCardHeader, MatCardSubtitle, MatCardTitle} from "@angular/material/card";
import {CalendarModule} from "primeng/calendar";
import {DropdownModule} from "primeng/dropdown";
import {InputTextModule} from "primeng/inputtext";
import {InputTextareaModule} from "primeng/inputtextarea";
import {ConferenceService} from "../../services/conference.service";
import {DatePipe, KeyValuePipe, NgForOf, NgIf, TitleCasePipe} from "@angular/common";
import {LocationService} from "../../services/location.service";
import {MessageModule} from "primeng/message";
import {MessagesModule} from "primeng/messages";
import {Conference} from "../../models/conference";
import {Router} from "@angular/router";
import {catchError} from "rxjs";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
    selector: 'app-conference-create',
    standalone: true,
    imports: [
        SidenavComponent,
        MatStepper,
        MatStep,
        MatStepLabel,
        MatFormField,
        MatButton,
        MatStepperNext,
        ReactiveFormsModule,
        MatInput,
        MatStepperPrevious,
        MatCard,
        MatCardContent,
        MatCardHeader,
        MatCardTitle,
        CalendarModule,
        DropdownModule,
        InputTextModule,
        InputTextareaModule,
        MatError,
        NgIf,
        MessageModule,
        MessagesModule,
        NgForOf,
        KeyValuePipe,
        TitleCasePipe,
        MatCardTitle,
        MatCardSubtitle,
    ],
    templateUrl: './conference-create.component.html',
    styleUrl: './conference-create.component.css',
    providers: [DatePipe]

})
export class ConferenceCreateComponent implements OnInit {
    conferenceFormGroup: FormGroup = new FormGroup({});
    locationFormGroup: FormGroup = new FormGroup({});
    conference: Conference = {} as Conference;

    constructor(
        private fb: FormBuilder,
        private conferenceService: ConferenceService,
        public datePipe: DatePipe,
        private locationService: LocationService,
        private router: Router,
        private snackBar: MatSnackBar
    ) {
    }

    ngOnInit(): void {
        this.initializeConferenceForm();
        this.initializeLocationForm();
    }


    private initializeConferenceForm() {
        const currentDate = new Date();
        const currentMinutes = currentDate.getMinutes();
        const minutesToAdd = currentMinutes % 5 === 0 ? 0 : 5 - (currentMinutes % 5);

        currentDate.setMinutes(currentMinutes + minutesToAdd);

        this.conferenceFormGroup = this.fb.group({
            name: ['', [Validators.required, Validators.minLength(3)]],
            description: ['', [Validators.required, Validators.minLength(10)]],
            start_date: [currentDate, [Validators.required, this.todayOrLaterValidator()]],
            end_date: [currentDate, [Validators.required, this.todayOrLaterValidator(),this.endDateAfterStartDateValidator()]],
        });
    }

    private initializeLocationForm() {
        this.locationFormGroup = this.fb.group({
            street: ['', Validators.required],
            number: ['', Validators.required],
            city: ['', Validators.required],
            postal_code: ['', Validators.required],
            country: ['', Validators.required],
            extraInfo: ['']
        });
    }

    //TODO globalize?
    markFormGroupTouched(formGroup: FormGroup): void {
        (Object as any).values(formGroup.controls).forEach((control: any) => {
            control.markAsTouched();

            if (control instanceof FormGroup) {
                this.markFormGroupTouched(control);
            }
        });
    }

    todayOrLaterValidator(): ValidatorFn {
        return (control: AbstractControl): ValidationErrors | null => {
            const selectedDate = control.value;
            const today = new Date();

            if (selectedDate && selectedDate < today) {
                return {todayOrLater: true};
            }

            return null;
        };
    }

    endDateAfterStartDateValidator(): ValidatorFn {
        return (control: AbstractControl): ValidationErrors | null => {
            const startDate = this.conferenceFormGroup.get('start_date')?.value as Date;
            const endDate = control.value as Date;

            console.log('Start date:', startDate);
            console.log('End date:', endDate);

            if (startDate && endDate && endDate <= startDate) {
                return { endDateBeforeStartDate: true };
            }

            return null;
        };
    }


    protected readonly Date = Date;

    formatDates(value: string) {
        return this.datePipe.transform(new Date(value), 'MM/dd/yyyy HH:mm');
    }

    createConference() {
        if (this.locationFormGroup.valid) {
            const location = this.locationFormGroup.value;
            this.locationService.createLocation(location).pipe(
                catchError((error) => {
                    console.error('Error creating location:', error);
                    const errorMessage = error && error.error ? error.error : 'Unknown error';
                    this.snackBar.open(errorMessage, 'Close', { duration: 5000 });
                    throw error;
                })
            ).subscribe((locationData) => {
                console.log('Location created', locationData);

                if (this.conferenceFormGroup.valid && locationData) {
                    const formValues = this.conferenceFormGroup.value;

                    this.conference.name = formValues.name;
                    this.conference.description = formValues.description;
                    this.conference.start_date = this.datePipe.transform(formValues.start_date, 'yyyy-MM-ddTHH:mm:ss.SSSSSS')!;
                    this.conference.end_date = this.datePipe.transform(formValues.end_date, 'yyyy-MM-ddTHH:mm:ss.SSSSSS')!;
                    this.conference.location_id = locationData.location_id.toString();
                    this.conference.active = false;
                    this.conference.floorPlanImage = '';
                    this.conference.talks = [];

                    this.conferenceService.createConference(this.conference).pipe(
                        catchError((error) => {
                            console.error('Error creating conference:', error);
                            const errorMessage = error && error.error ? error.error : 'Unknown error';
                            this.snackBar.open(errorMessage, 'Close', { duration: 5000 });
                            throw error;
                        })
                    ).subscribe((conferenceData) => {
                        console.log('Conference created', conferenceData);
                        this.router.navigate(['conferences']).then(r => console.log('Navigated to conferences'));
                        this.snackBar.open('Conference created', 'Close', { duration: 3000 });
                    });
                } else {
                    this.snackBar.open('Form is invalid', 'Close', { duration: 3000 });
                }
            });
        } else {
            this.snackBar.open('Form is invalid', 'Close', { duration: 3000 });
        }
    }
}
