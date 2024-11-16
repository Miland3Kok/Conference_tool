import {Component, OnInit} from '@angular/core';
import {RouterOutlet, Router, RouterLink} from '@angular/router';
import {LoginFormComponent} from "./components/login-form/login-form.component";
import {MatGridList, MatGridTile} from "@angular/material/grid-list";
import {MatCard, MatCardContent, MatCardHeader, MatCardTitleGroup} from "@angular/material/card";
import {MatListItem} from "@angular/material/list";

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [RouterOutlet, LoginFormComponent, MatGridList, MatGridTile, MatCard, MatCardHeader, MatCardTitleGroup, MatCardContent, MatListItem, RouterLink],
    templateUrl: './app.component.html',
    styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
    title = 'conference-tool';

    constructor(private router: Router) {
    }

    ngOnInit(): void {
    }

}
