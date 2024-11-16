import {APP_INITIALIZER, ApplicationConfig} from '@angular/core';
import {provideRouter} from '@angular/router';
import {routes} from './app.routes';
import {HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi} from '@angular/common/http';
import {provideAnimationsAsync} from "@angular/platform-browser/animations/async";
import {provideClientHydration} from "@angular/platform-browser";
import {KeycloakService} from "keycloak-angular";
import {initializeKeycloak} from "./security-config/keycloak.config";
import {AuthInterceptor} from "./security-config/auth.interceptor";

export const appConfig: ApplicationConfig = {
    providers: [
        provideRouter(routes),
        provideHttpClient(),
        provideAnimationsAsync(),
        provideClientHydration(),
        {
            provide: APP_INITIALIZER,
            useFactory: initializeKeycloak,
            multi: true,
            deps: [KeycloakService]
        },
        provideHttpClient(withInterceptorsFromDi()),
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true
        },
        KeycloakService
    ]
};
