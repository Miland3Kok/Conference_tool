import { Component } from '@angular/core';
import {SidenavComponent} from "../sidenav/sidenav.component";

@Component({
  selector: 'app-notifications',
  standalone: true,
  imports: [
    SidenavComponent
  ],
  templateUrl: './notifications.component.html',
  styleUrl: './notifications.component.css'
})
export class NotificationsComponent {

}
