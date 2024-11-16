import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {asyncScheduler, catchError, Observable, observeOn, throwError} from "rxjs";
import {Talk} from "../models/talk";
import {Injectable} from "@angular/core";
import {UserToken} from "../security-config/userToken.service";
import {environment} from "../../environments/environment";

@Injectable({
    providedIn: 'root'
})

export class TalkService {
    private API_URL: string = environment.backendConfig.url + 'api/talk';
    private userToken: string | undefined;

    constructor(private http: HttpClient, userToken: UserToken) {
        userToken.getUserToken().then(token => {
            this.userToken = token;
        });
    }

    getTalkById(talkId: string): Observable<Talk> {
        return this.http.get<Talk>(`${this.API_URL}/${talkId}`).pipe(
            catchError(this.handleError)
        );
    }

    updateTalk(talk: Talk): Observable<Talk> {
        return this.http.put<Talk>(`${this.API_URL}/update`, talk).pipe(
            catchError(this.handleError)
        );
    }

    addSpeakerToTalk(talkId: string, speakerId: string): Observable<Talk> {
        return this.http.post<Talk>(`${this.API_URL}/addSpeaker`, {
            talkId: talkId,
            speakerId: speakerId
        }).pipe(
            catchError(this.handleError)
        );
    }

    removeSpeakerFromTalk(talkId: string, speakerId: string): Observable<any> {
        return this.http.delete<any>(`${this.API_URL}/removeSpeaker`, {
            body: {
                talkId: talkId,
                speakerId: speakerId
            }
        }).pipe(
            catchError(this.handleError)
        );
    }

    createTalk(talk: Talk): Observable<Talk> {
        return this.http.post<Talk>(`${this.API_URL}/create`, talk).pipe(
            catchError(this.handleError)
        );
    }

    deleteTalk(talkId: string): Observable<any> {
        return this.http.delete(`${this.API_URL}/delete/${talkId}`).pipe(
            catchError(this.handleError)
        );
    }

    getFavouriteTalks(userId: string, conferenceId: string): Observable<Talk[]> {
        return this.http.get<Talk[]>(`${this.API_URL}/favourite/${userId}/${conferenceId}`).pipe(
            catchError(this.handleError)
        );
    }

    async favouriteTalk(userId: string, talkId: string): Promise<Talk> {
        const response = await fetch(`${this.API_URL}/favourite?userId=${userId}&talkId=${talkId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + this.userToken
            },
            body: JSON.stringify({})
        });
        if (response.ok) {
            return response.text().then(text => text ? JSON.parse(text) : {});
        } else {
            throw new Error('Server response was not ok.');
        }
    }

    unfavouriteTalk(userId: string, talkId: string): Observable<Talk> {
        return this.http.delete<Talk>(`${this.API_URL}/removeFavourite?userId=${userId}&talkId=${talkId}`).pipe(
            catchError(this.handleError)
        );
    }

    getTalksBySpeakerId(speakerId: string): Observable<Talk[]> {
        console.log(speakerId)
        return this.http.get<Talk[]>(`${this.API_URL}/getTalksBySpeakerId/${speakerId}`).pipe(
            catchError(this.handleError)
        );
    }

    getFeaturedTalks(conferenceId: string): Observable<Talk[]> {
        return this.http.get<Talk[]>(`${this.API_URL}/getFeaturedTalksOfConference/${conferenceId}`).pipe(
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
