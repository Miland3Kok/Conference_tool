import {Injectable} from "@angular/core";
import {KeycloakService} from "keycloak-angular";
import {KeycloakProfile} from "keycloak-js";

interface CustomKeycloakProfile extends KeycloakProfile {
    attributes?: {
        [key: string]: string[];
    };
}
@Injectable({
    providedIn: 'root'
})
export class UserToken {
    constructor(private keycloakService: KeycloakService) {
    }
    async getUserToken(): Promise<string> {
        return await this.keycloakService.getToken();
    }

    async getUserId() : Promise<string> {
        const userProfile = await this.keycloakService.loadUserProfile();
        if (userProfile) {
            return userProfile.id || "";
        }
        return "";
    }

    async getUserRoles(): Promise<string[]> {
        return this.keycloakService.getUserRoles();
    }

    async getUserName(): Promise<string | undefined> {
        const userProfile = await this.keycloakService.loadUserProfile();
        return userProfile.username
    }

    async getFunction(): Promise<string> {
        try {
            const userProfile: CustomKeycloakProfile = await this.keycloakService.loadUserProfile() as CustomKeycloakProfile;
            return userProfile.attributes?.["function"][0] || "Axxes";
        } catch (error) {
            console.error('Error fetching user profile:', error);
            return "Axxes";
        }
    }

    async getUserEmail(): Promise<string | undefined> {
        const userProfile: KeycloakProfile = await this.keycloakService.loadUserProfile();
        return userProfile.email;
    }

    async getFullName(): Promise<string> {
        const userProfile = await this.keycloakService.loadUserProfile();
        const firstName = userProfile.firstName;
        const lastName = userProfile.lastName;
        return `${firstName} ${lastName}`;
    }

    isTokenExpired() {
        return this.keycloakService.isTokenExpired();
    }

    renewToken() {
        this.keycloakService.updateToken();
    }
}
