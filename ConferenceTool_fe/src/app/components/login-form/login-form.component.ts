import { Component } from '@angular/core';
import {FormBuilder, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import { Router } from '@angular/router';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatIcon} from "@angular/material/icon";
import {MatAnchor, MatButton, MatIconButton} from "@angular/material/button";
import {MatSlideToggle} from "@angular/material/slide-toggle";
import {MatDivider} from "@angular/material/divider";
@Component({
  selector: 'app-login-form',
  standalone: true,
    imports: [MatFormFieldModule, MatInputModule, FormsModule, ReactiveFormsModule, MatIcon, MatIconButton, MatSlideToggle, MatButton, MatAnchor, MatDivider],
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.css'
})
export class LoginFormComponent {
  public userDetails:any;
  signInForm:any;

  constructor(private router: Router) { }

  onLoginClick(){
    this.router.navigate(['/home']);
  }

  ngOnInit(): void {
    /*
    if(this.service.isUserLoggedIn){
      this.router.navigate(['/home']);
    }

     */
  }



  onInitSignInForm(){
  }
  public getEmail(){
    return this.signInForm.get('email');
  }
  public getPassword(){
    return this.signInForm.get('password');
  }
  onSubmitSignInForm(){
  }
}
