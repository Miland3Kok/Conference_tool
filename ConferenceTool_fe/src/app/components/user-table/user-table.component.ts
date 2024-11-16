import {Component, OnInit} from '@angular/core';
import {SidenavComponent} from "../sidenav/sidenav.component";
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef,
  MatTable, MatTableDataSource
} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {UserService} from "../../services/user.service";
import {MessageObject} from "../../models/message";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatOption, MatSelect} from "@angular/material/select";
import {NgForOf, NgIf} from "@angular/common";
import {MatButton} from "@angular/material/button";
import {MatInput} from "@angular/material/input";
import {Router, RouterLink} from "@angular/router";

@Component({
  selector: 'app-user-table',
  standalone: true,
    imports: [
        SidenavComponent,
        MatTable,
        MatColumnDef,
        MatHeaderCell,
        MatCell,
        MatHeaderCellDef,
        MatCellDef,
        MatHeaderRow,
        MatRow,
        MatRowDef,
        MatHeaderRowDef,
        MatPaginator,
        MatFormField,
        MatSelect,
        MatOption,
        NgForOf,
        MatLabel,
        MatButton,
        MatInput,
        NgIf,
        RouterLink
    ],
  templateUrl: './user-table.component.html',
  styleUrl: './user-table.component.css'
})
export class UserTableComponent implements OnInit {
    displayedColumns: string[] = ['username', 'firstname', 'lastname', 'email','makeSpeaker', 'delete'];
    dataSource: MatTableDataSource<MessageObject> = new MatTableDataSource<MessageObject>([]);
    roles: any[] = [];
    selectedRole: string = '';
    speakerIds: String[] = [];


    constructor(private userService: UserService) {}

    ngOnInit() {
        this.userService.getRoles().subscribe((roles: string[]) => {
            this.roles = Object.entries(roles).map(([id, name]) => ({ id: +id, name }));
            this.selectedRole = this.roles[0].name;
            this.fetchUsersInRole();
        });

        this.userService.getAllSpeakerIds().subscribe((ids: String[]) => {
            this.speakerIds = ids;
            console.log(this.speakerIds);
        });
    }

    onRoleChange() {
        this.fetchUsersInRole();
    }

    fetchUsersInRole() {
        this.userService.getUsersInRole(this.selectedRole).subscribe((data: any) => {
            this.dataSource.data = data;
        });
    }

    deleteUser(id: string) {
        this.userService.deleteUser(id).subscribe(() => {
            this.fetchUsersInRole();
        });
    }

    makeSpeaker(id: string) {
        this.userService.createSpeaker(id).subscribe(() => {
            this.userService.addRoleToUser(id, 'speaker').then(() => { // Add the role 'speaker' to the user
                this.fetchUsersInRole();
            });
        });
    }

    applyFilter(filterValue: string) {
        console.log(filterValue);
        filterValue = filterValue.trim().toLowerCase();
        this.dataSource.filter = filterValue;
    }
}
