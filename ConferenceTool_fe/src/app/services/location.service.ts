import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Location} from "../models/location";
import {environment} from "../../environments/environment";
import {asyncScheduler, catchError, Observable, observeOn, throwError} from "rxjs";

@Injectable({
    providedIn: 'root'
})

export class LocationService {

    private API_URL: string = environment.backendConfig.url + 'api/location'

    constructor(private http: HttpClient) {
    }

    getAllLocations() {
        return this.http.get<Location[]>(this.API_URL).pipe(
            catchError(this.handleError)
        );
    }

    getLocationById(id: string) {
        return this.http.get<Location>(`${this.API_URL}/${id}`).pipe(
            catchError(this.handleError)
        );
    }

    createLocation(location: Location) {
        return this.http.post<Location>(`${this.API_URL}/create`, location).pipe(
            catchError(this.handleError)
        );
    }

    updateLocation(location: Location) {
        return this.http.put<Location>(`${this.API_URL}/update`, location).pipe(
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
