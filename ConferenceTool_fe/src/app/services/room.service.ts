import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {asyncScheduler, catchError, Observable, observeOn, throwError} from 'rxjs';
import {Room} from "../models/room";
import {environment} from "../../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class RoomService {

    private API_URL: string = environment.backendConfig.url + 'api/room';

    constructor(private http: HttpClient) {
    }

    getRoomById(roomId: string): Observable<Room> {
        return this.http.get<Room>(`${this.API_URL}/${roomId}`).pipe(
            catchError(this.handleError)
        );
    }

    updateRoom(room: Room): Observable<Room> {
        return this.http.put<Room>(`${this.API_URL}/update`, room).pipe(
            catchError(this.handleError)
        );
    }


    createRoom(room: Room): Observable<Room> {
        return this.http.post<Room>(`${this.API_URL}/create`, room).pipe(
            catchError(this.handleError)
        );
    }

    deleteRoom(roomId: string): Observable<any> {
        return this.http.delete(`${this.API_URL}/delete/${roomId}`).pipe(
            catchError(this.handleError)
        );
    }

    getRoomsByConferenceId(id: string): Observable<Room[]> {
        return this.http.get<Room[]>(`${this.API_URL}/GetRoomsByConferenceId/${id}`).pipe(
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
