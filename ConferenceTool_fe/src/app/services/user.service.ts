import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {asyncScheduler, catchError, Observable, observeOn, throwError} from "rxjs";
import {User} from "../models/user";
import {Speaker} from "../models/speaker";
import {UserToken} from "../security-config/userToken.service";
import {environment} from "../../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private API_URL: string = environment.backendConfig.url + 'api/keycloak';
    private userToken: string | undefined;
    private API_USER_URL= environment.backendConfig.url + 'api/user';
    private API_SPEAKER_URL = environment.backendConfig.url + 'api/speaker';

    constructor(private http: HttpClient, userToken: UserToken) {
        userToken.getUserToken().then(token => {
            this.userToken = token;
        });
    }

    getRoles() {
        const url = `${this.API_URL}/getRoles`;
        return this.http.get<any>(url).pipe(
                catchError(this.handleError)
        );
    }

    addRoleToUser(userId: string, role: string,) {
        return fetch (`${this.API_URL}/addRole/${userId}/${role}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + this.userToken
            },
            body: JSON.stringify({})
        }).then(response => response.json()).catch(catchError(this.handleError));
    }

    createUser(userData: any) {
        const url = `${this.API_URL}/create`;
        return this.http.post<any>(url, userData).pipe(
            catchError(this.handleError)
        );
    }

    uploadFile(selectedFile: File) {
        const url = `${this.API_URL}/upload`;
        const formData = new FormData();
        formData.append('file', selectedFile);

        return this.http.post<any>(url, formData).pipe(
            catchError(this.handleError)
        );
    }

    getUser(userId: string) {
        return this.http.get<User>(`${this.API_USER_URL}/${userId}`).pipe(
            catchError(this.handleError)
        );
    }

    getUsersInRole(role: string) {
        return this.http.get<User[]>(`${this.API_URL}/get/${role}`).pipe(
            catchError(this.handleError)
        );
    }

    deleteUser(id: string) {
        const url = `${this.API_URL}/delete/${id}`;
        return this.http.delete(url).pipe(
            catchError(this.handleError)
        );
    }

    createSpeaker(userId: string) {
        return this.http.post<Speaker>(`${this.API_SPEAKER_URL}/createSpeakerWithId?userId=${userId}`, null).pipe(
                catchError(this.handleError)
            );
    }

    getAllSpeakerIds() {
        return this.http.get<String[]>(`${this.API_SPEAKER_URL}/getUserIdsOfSpeakers`).pipe(
            catchError(this.handleError)
        );
    }

    private handleError(error: HttpErrorResponse): Observable<never> {
        console.error('An error occurred:', error.error.message || error.statusText);
        return throwError(() => new Error(error.error.message)).pipe(
            observeOn(asyncScheduler)
        );
    }

    uploadAvatar(userId: string, avatar: File): Observable<any> {
        const formData = new FormData();
        formData.append('file', avatar);
        formData.append('userId', userId);
        return this.http.post(`${this.API_USER_URL}/upload-avatar`, formData);
    }

    getProfilePictureOfUser(userId: string): Observable<Blob> {
        return this.http.get(`${this.API_USER_URL}/getProfilePictureOfUser/${userId}`, { responseType: 'blob' });
    }
}
