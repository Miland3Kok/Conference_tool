import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {asyncScheduler, catchError, Observable, observeOn, throwError} from 'rxjs';
import {Conference} from "../models/conference";
import {Room} from "../models/room";
import {Talk} from "../models/talk";
import {environment} from "../../environments/environment";

@Injectable({
    providedIn: 'root'
})

export class ConferenceService {

    private API_URL: string = environment.backendConfig.url + 'api/conference';

    constructor(private http: HttpClient) {}

    getAllConferences(): Observable<Conference[]> {
        return this.http.get<Conference[]>(`${this.API_URL}/all`).pipe(
            catchError(this.handleError)
        );
    }

    getConferenceById(id: string) {
        return this.http.get<Conference>(`${this.API_URL}/${id}`).pipe(
            catchError(this.handleError)
        );
    }

    toggleConferenceStatus(id: number): Observable<Conference> {
        return this.http.put<Conference>(`${this.API_URL}/toggle-status/${id}`, null).pipe(
            catchError(this.handleError)
        );
    }

    updateConference(conference: Conference): Observable<Conference> {
        return this.http.put<Conference>(`${this.API_URL}/update`, conference).pipe(
            catchError(this.handleError)
        );
    }

    createConference(conference: Conference): Observable<Conference> {
        return this.http.post<Conference>(`${this.API_URL}/create`, conference).pipe(
            catchError(this.handleError)
        );
    }

    getActiveConference(): Observable<Conference> {
        return this.http.get<Conference>(`${this.API_URL}/get-active`).pipe(
            catchError(this.handleError)
        );
    }

    getRoomsForConference(conferenceId: number): Observable<Room[]> {
        return this.http.get<Room[]>(`${this.API_URL}/${conferenceId}/rooms`).pipe(
            catchError(this.handleError)
        );
    }

    getTalksForConference(conferenceId: number): Observable<Talk[]> {
        return this.http.get<Talk[]>(`${this.API_URL}/${conferenceId}/talks`).pipe(
            catchError(this.handleError)
        );
    }

    uploadFloorPlan(conferenceId: string, floorPlan: File): Observable<any> {
        const formData = new FormData();
        formData.append('file', floorPlan);
        formData.append('conferenceId', conferenceId);
        return this.http.post(`${this.API_URL}/upload-floorplan`, formData).pipe(
            catchError(this.handleError)
        );
    }

    getFloorplan(conferenceId: string): Observable<Blob> {
        return this.http.get(`${this.API_URL}/${conferenceId}/floorplan`, { responseType: 'blob' }).pipe(
            catchError(this.handleError)
        );
    }

    private handleError(error: HttpErrorResponse): Observable<never> {
        console.error('An error occurred:', error.error.message || error.statusText);
        return throwError(() => new Error(error.error.message)).pipe(
            observeOn(asyncScheduler)
        );
    }
}
