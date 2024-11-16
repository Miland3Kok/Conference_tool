import {Component} from '@angular/core';
import {SidenavComponent} from "../sidenav/sidenav.component";
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from "@angular/material/card";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {LocationService} from "../../services/location.service";
import {InputTextModule} from "primeng/inputtext";
import {MatButton} from "@angular/material/button";
import {InputNumberModule} from "primeng/inputnumber";

@Component({
    selector: 'app-location-create',
    standalone: true,
  imports: [
    SidenavComponent,
    MatCard,
    MatCardHeader,
    MatCardContent,
    MatCardTitle,
    InputTextModule,
    ReactiveFormsModule,
    MatButton,
    InputNumberModule
  ],
    templateUrl: './location-create.component.html',
    styleUrl: './location-create.component.css'
})
export class LocationCreateComponent {
    locationForm: FormGroup = this.fb.group({
        street: ['', Validators.required],
        number: ['', Validators.required],
        city: ['', Validators.required],
        postal_code: ['', Validators.required],
        country: ['', Validators.required],
        extraInfo: ['']
    });

    constructor(private fb: FormBuilder, private locationService: LocationService) {
    }

    createLocation() {
        if (this.locationForm.valid) {
            const location = this.locationForm.value;
            this.locationService.createLocation(location).subscribe((data) => {
                console.log('Location created', data);
            });
        } else {
            console.log('Form is invalid');
        }
    }
}