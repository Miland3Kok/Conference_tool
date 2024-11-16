import {Component, HostListener, Input, OnInit} from '@angular/core';
import {MatListModule} from "@angular/material/list";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatCheckbox} from "@angular/material/checkbox";
import {FormsModule} from "@angular/forms";
import {NgClass, NgIf, NgOptimizedImage} from "@angular/common";
import {Router, RouterLink} from "@angular/router";
import {MatCard, MatCardContent, MatCardTitle} from "@angular/material/card";
import {MatChipAvatar} from "@angular/material/chips";
import {MatGridList, MatGridTile} from "@angular/material/grid-list";
import {UserToken} from "../../security-config/userToken.service";
import {KeycloakService} from "keycloak-angular";
import {SpeakerService} from "../../services/speaker.service";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-sidenav',
  imports: [MatToolbarModule, MatButtonModule, MatIconModule, MatSidenavModule, MatListModule, MatCheckbox, FormsModule, NgClass, RouterLink, MatCard, MatChipAvatar, MatCardTitle, MatCardContent, MatGridList, MatGridTile, NgOptimizedImage, NgIf],
  templateUrl: './sidenav.component.html',
  standalone: true,
  styleUrl: './sidenav.component.css'
})
export class SidenavComponent implements OnInit {
  isExpanded: boolean = true;
  @Input () title: string = "Conference Tool";
  @Input () userName: string = "User";
  @Input () function: string = "User";
  @Input () roles: string[] = [];
  profilePicture: SafeUrl | undefined = undefined;
  private sanitizer: DomSanitizer;
  userId: string='';


  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.isExpanded = window.innerWidth > 1200;
  }

  constructor(private UserToken: UserToken, private keycloakService: KeycloakService,
              private router: Router, sanitizer: DomSanitizer, private userService: UserService) {

    this.sanitizer = sanitizer;
    this.initUserId(UserToken);

  }

    async initUserId(UserToken: UserToken) : Promise<void> {
        this.userId = await UserToken.getUserId();
    }

  toggleCollapse(): void {
    this.isExpanded = !this.isExpanded;
  }

  async ngOnInit() : Promise<void> {
    this.onResize(null); // Initialize isExpanded property
    this.userName = await this.UserToken.getFullName();
    this.roles = await this.UserToken.getUserRoles();
    this.function = await this.UserToken.getFunction();

    this.userService.getProfilePictureOfUser(this.userId).subscribe(blob => {
      let objectURL = URL.createObjectURL(blob);
      this.profilePicture = this.sanitizer.bypassSecurityTrustUrl(objectURL);
    });
  }

  isAuthorized(requiredRole: string[]): boolean {
    return this.roles.some(role => requiredRole.includes(role));
  }

  logout() {
    localStorage.removeItem('bearerToken');
    this.keycloakService.logout().then(() => {
      this.router.navigate(['/login']).then(r => console.log('navigated to login'));
    });
  }
}
