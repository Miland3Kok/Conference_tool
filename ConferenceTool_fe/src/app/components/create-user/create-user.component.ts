import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {SidenavComponent} from "../sidenav/sidenav.component";
import {InputTextModule} from "primeng/inputtext";
import {InputTextareaModule} from "primeng/inputtextarea";
import {MatButton} from "@angular/material/button";
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from "@angular/material/card";
import {catchError} from "rxjs";
import {NgForOf} from "@angular/common";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-create-user',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    SidenavComponent,
    InputTextModule,
    InputTextareaModule,
    MatButton,
    MatCard,
    MatCardContent,
    MatCardHeader,
    MatCardTitle,
    NgForOf
  ],
  templateUrl: './create-user.component.html',
  styleUrl: './create-user.component.css'
})
export class CreateUserComponent implements OnInit {
  createUser: FormGroup = new FormGroup({});
  roles: any[] = [];
  selectedFile: File | null = null;

  constructor(private formBuilder: FormBuilder, private userService: UserService, private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.createUser = this.formBuilder.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      firstname: ['', [Validators.required]],
      lastname: ['', [Validators.required]],
      office_function: ['', [Validators.required]],
      role: ['', [Validators.required]]
    });

    this.getRoles()
  }

  getRoles() {
    this.userService.getRoles().pipe(
        catchError(error => {
          console.error('Error fetching roles:', error);
          return [];
        })
    ).subscribe(
        roles => {
          this.roles = Object.entries(roles).map(([id, name]) => ({ id: +id, name }));
        }
    );
  }

  onSubmit() {
    if (this.createUser.valid) {
      const { username, email, firstname, lastname, office_function, role } = this.createUser.value;

      const userData = {
        firstname: firstname,
        lastname: lastname,
        function: office_function,
        username: username,
        email: email,
        role: role
      };
      this.userService.createUser(userData).pipe(
          catchError((error) => {
            console.error('Error creating user:', error);
            const errorMessage = error && error.error ? error.error : 'Unknown error';
            this.snackBar.open(errorMessage, 'Close', { duration: 5000 });
            throw error;
          })
      ).subscribe(() => {
        console.log('User created successfully.');
        this.createUser.reset();
        this.snackBar.open('User created', 'Close', { duration: 3000 });
      });
    }
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  uploadFile() {
    if (this.selectedFile) {
      this.userService.uploadFile(this.selectedFile).pipe(
          catchError((error) => {
            console.error('Error uploading file:', error);
            const errorMessage = error && error.error ? error.error : 'Unknown error';
            this.snackBar.open(errorMessage, 'Close', { duration: 5000 });
            throw error;
          })
      ).subscribe(() => {
        console.log('File uploaded successfully.');
        this.snackBar.open('File uploaded', 'Close', { duration: 3000 });
      });
    }
  }
}
