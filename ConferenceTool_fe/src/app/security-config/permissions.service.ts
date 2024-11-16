import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import {UserToken} from "./userToken.service";

@Injectable({
    providedIn: 'root'
})
export class Permissions {
    constructor(private keycloakService: KeycloakService) {
    }
    async canActivate(user: UserToken, requiredRole: string[]): Promise<boolean> {
        const isLoggedIn = this.keycloakService.isLoggedIn();
        if (!isLoggedIn) {
            await this.keycloakService.login();
            return false;
        }

        const roles = this.keycloakService.getUserRoles();
        return roles.some(role => requiredRole.includes(role));
    }
}
