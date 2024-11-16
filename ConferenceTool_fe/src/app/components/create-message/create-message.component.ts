import {Component, OnInit} from '@angular/core';
import {SidenavComponent} from "../sidenav/sidenav.component";
import {CalendarModule} from "primeng/calendar";
import {DropdownModule} from "primeng/dropdown";
import {InputTextModule} from "primeng/inputtext";
import {InputTextareaModule} from "primeng/inputtextarea";
import {MatButton} from "@angular/material/button";
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from "@angular/material/card";
import {PaginatorModule} from "primeng/paginator";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatFormField} from "@angular/material/form-field";
import {MessagesService} from "../../services/messages.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-create-message',
  standalone: true,
  imports: [
    SidenavComponent,
    CalendarModule,
    DropdownModule,
    InputTextModule,
    InputTextareaModule,
    MatButton,
    MatCard,
    MatCardContent,
    MatCardHeader,
    MatCardTitle,
    PaginatorModule,
    ReactiveFormsModule,
    MatFormField
  ],
  templateUrl: './create-message.component.html',
  styleUrl: './create-message.component.css'
})
export class CreateMessageComponent implements OnInit {
  messageForm: FormGroup = new FormGroup({});

    constructor(private fb: FormBuilder, private messagesService: MessagesService, private snackBar: MatSnackBar) {}

    ngOnInit(): void {
        this.initializeForm();
    }

    initializeForm() {
        this.messageForm = this.fb.group({
            subject: ['', Validators.required],
            message: ['', Validators.required],
            important: [false]
        });
    }

    createMessage() {
        if(this.messageForm.valid) {
          const formValues = this.messageForm.value;

          this.messagesService.createMessage(formValues).subscribe(() => {
            console.log('Message created');
          });

          this.messageForm.reset();

          this.snackBar.open('Message created', 'Close', {
            duration: 3000
          });
        }
    }
}
