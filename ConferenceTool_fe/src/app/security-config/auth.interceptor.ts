import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import {UserToken} from "./userToken.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    constructor(private tokenService: UserToken) {
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (this.tokenService.isTokenExpired()) {
            this.tokenService.renewToken();
        }

        const token = localStorage.getItem('bearerToken');

        if (token) {
            req = req.clone({
                setHeaders: {
                    Authorization: `bearer ${token}`
                }
            });
        }

        return next.handle(req);
    }
}
