import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {asyncScheduler, catchError, Observable, observeOn, throwError} from "rxjs";
import {MessageObject} from "../models/message";
import {environment} from "../../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class MessagesService {

    private API_URL: string = environment.backendConfig.url + 'api/message'

    constructor(private http: HttpClient) {
    }

    getAllMessagesForOrganisation(): Observable<MessageObject[]> {
        return this.http.get<MessageObject[]>(`${this.API_URL}/getMessagesForOrganisation`)
            .pipe(
                catchError(this.handleError)
            );
    }

    getMessageById(messageId: string): Observable<MessageObject> {
        const url = `${this.API_URL}/getMessageDetails`;
        return this.http.get<MessageObject>(url, {params: {messageId}})
            .pipe(
                catchError(this.handleError)
            );
    }

    createMessage(message: MessageObject): Observable<MessageObject> {
        return this.http.post<MessageObject>(`${this.API_URL}/createOrganisation`, message)
            .pipe(
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
