import { Component, OnInit } from '@angular/core';
import {LoginFormComponent} from "../login-form/login-form.component";
import {MatCard, MatCardContent} from "@angular/material/card";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    LoginFormComponent,
    MatCard,
    MatCardContent
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  constructor(private router: Router) {
  }

}
