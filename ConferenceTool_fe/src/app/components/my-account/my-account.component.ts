import {Component, OnInit} from '@angular/core';
import {SidenavComponent} from "../sidenav/sidenav.component";
import {MatButton} from "@angular/material/button";
import {UserToken} from "../../security-config/userToken.service";
import {User} from "../../models/user";
import {UserService} from "../../services/user.service";
import {MatGridList, MatGridTile} from "@angular/material/grid-list";
import {FileUploadModule} from 'primeng/fileupload';
import { MatSnackBar } from '@angular/material/snack-bar';


import {
    MatCard,
    MatCardActions,
    MatCardContent,
    MatCardHeader,
    MatCardImage, MatCardSubtitle,
    MatCardTitle
} from "@angular/material/card";
import {MatSortHeader} from "@angular/material/sort";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {FormBuilder, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {MatIcon} from "@angular/material/icon";
import {Speaker} from "../../models/speaker";
import {SpeakerService} from "../../services/speaker.service";
import {CdkTextareaAutosize} from "@angular/cdk/text-field";
import {NgClass, NgIf, NgStyle} from "@angular/common";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";

@Component({
    selector: 'app-my-account',
    standalone: true,
    imports: [
        SidenavComponent,
        MatButton,
        MatGridList,
        MatGridTile,
        MatCard,
        MatCardHeader,
        MatCardImage,
        MatCardTitle,
        MatCardSubtitle,
        MatCardContent,
        MatCardActions,
        MatSortHeader,
        MatFormField,
        MatInput,
        ReactiveFormsModule,
        MatLabel,
        MatIcon,
        CdkTextareaAutosize,
        NgIf,
        NgClass,
        NgStyle,
        FileUploadModule,
    ],
    templateUrl: './my-account.component.html',
    styleUrls: ['./my-account.component.css']
})
export class MyAccountComponent implements OnInit {
    token: string | undefined;
    username: string | undefined;
    email: string | undefined;
    fullname: string | undefined;
    userForm: FormGroup;
    user: User | undefined = undefined;
    Speaker: Speaker | undefined = undefined;
    breakpoint: number = 2;
    userId: string = '';
    profilePicture: SafeUrl | undefined = undefined;
    private sanitizer: DomSanitizer;
    uploadedFiles: any[] = [];


    constructor(private userToken: UserToken,
                private UserService: UserService,
                private fb: FormBuilder,
                private SpeakerService: SpeakerService,
                sanitizer: DomSanitizer,
                private snackBar: MatSnackBar) {

        this.sanitizer = sanitizer;
        this.initUserId(userToken);

        this.userForm = this.fb.group({
            firstName: [{value: '', disabled: true}],
            lastName: [{value: '', disabled: true}],
            office_function: [{value: '', disabled: true}],
            email: [{value: '', disabled: true}],
            bio: [{value: '', disabled: false}],
            phone: [{value: '', disabled: false}],
        });
    }

    async initUserId(UserToken: UserToken) {
        this.userId = await UserToken.getUserId();
    }

    async ngOnInit(): Promise<void> {
        this.token = await this.userToken.getUserToken();
        this.username = await this.userToken.getUserName();
        this.email = await this.userToken.getUserEmail();
        this.fullname = await this.userToken.getFullName();

        this.UserService.getUser(this.userId).subscribe(user => {
            this.user = user;
            if (this.user) {
                this.UserService.getProfilePictureOfUser(user.user_id.toString()).subscribe(blob => {
                    let objectURL = URL.createObjectURL(blob);
                    this.profilePicture = this.sanitizer.bypassSecurityTrustUrl(objectURL);
                });
            }
            this.SpeakerService.checkIfUserIsSpeakerAndReturnSpeaker(user.user_id.toString()).subscribe(speaker => {
                this.Speaker = speaker;
            });
        });

        this.breakpoint = (window.innerWidth <= 1400) ? 1 : 2;

    }



    updateSpeaker() {
        let bio: string = this.userForm.get('bio')?.value || '';
        let phone: string = this.userForm.get('phone')?.value || '';
        if(this.userForm.valid && this.Speaker) {
            if (bio != null && phone != null) {
                this.SpeakerService.updateSpeaker(this.Speaker.speaker_id, bio, phone).subscribe(response => {
                    console.log(response);
                });
            }
        }
    }

    uploadAvatar() {
        const fileInput = document.getElementById('imageUpload') as HTMLInputElement;
        if (fileInput.files && fileInput.files[0]) {
            const file = fileInput.files[0];
            if (this.user) {
                this.UserService.uploadAvatar(this.user.user_id.toString(), file).subscribe({
                    next: (response) => {
                        this.snackBar.open('Profile picture updated', 'Close', {
                            duration: 3000
                        });
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
