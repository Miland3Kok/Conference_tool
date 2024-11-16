import {KeycloakService} from "keycloak-angular";
import { environment } from '../../environments/environment';

export function initializeKeycloak(keycloakService: KeycloakService): () => Promise<void> {
    return () => new Promise((resolve) => {
        keycloakService.init(
            {
                config: {
                    url: environment.keycloakConfig.url,
                    realm: environment.keycloakConfig.realm,
                    clientId: environment.keycloakConfig.clientId,
                },
                initOptions: {
                    onLoad: 'login-required',
                    checkLoginIframe: false
                },
                enableBearerInterceptor: true
            }
        ).then(() => {
            keycloakService.getToken().then(token => {
                if(token) {
                    localStorage.setItem('bearerToken', token);
                }
                resolve()
            });
        });
    });
}
