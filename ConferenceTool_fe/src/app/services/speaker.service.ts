import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {asyncScheduler, catchError, Observable, observeOn, throwError} from "rxjs";
import {Speaker} from "../models/speaker";
import {UpdateSpeakerDTO} from "../models/updateSpeakerDTO";
import {environment} from "../../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class SpeakerService {
    private API_URL: string = environment.backendConfig.url + 'api/speaker';

    constructor(private http: HttpClient) {
    }

    getAllSpeakers(): Observable<Speaker[]> {
        return this.http.get<Speaker[]>(`${this.API_URL}/all`).pipe(
            catchError(this.handleError)
        );
    }

    getSpeakersFromActiveConference(): Observable<Speaker[]> {
        return this.http.get<Speaker[]>(`${this.API_URL}/upcomingConference`).pipe(
            catchError(this.handleError)
        );
    }

    checkIfUserIsSpeakerAndReturnSpeaker(userId: string): Observable<Speaker> {
        return this.http.get<Speaker>(`${this.API_URL}/checkIfUserIsSpeakerAndReturnSpeaker?userId=${userId}`).pipe(
            catchError(this.handleError)
        );
    }

    checkIfUserIsSpeaker(userId: string): Observable<boolean> {
        return this.http.get<boolean>(`${this.API_URL}/checkIfUserIsSpeaker?userId=${userId}`).pipe(
            catchError(this.handleError)
        );
    }

    updateSpeaker(speakerId: number, bio: string, phone: string): Observable<UpdateSpeakerDTO> {
        let updateSpeakerDTO: UpdateSpeakerDTO = {
            speakerId: speakerId,
            bio: bio,
            phone: phone
        }
        return this.http.put<UpdateSpeakerDTO>(`${this.API_URL}/update`, updateSpeakerDTO).pipe(
            catchError(this.handleError)
        );
    }

    getSpeakerIdByUserId(userId: string): Observable<number> {
        return this.http.get<number>(`${this.API_URL}/getSpeakerIdByUserId?userId=${userId}`).pipe(
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
