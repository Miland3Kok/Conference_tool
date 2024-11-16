import {ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {inject} from '@angular/core';
import {UserToken} from "./userToken.service";
import {Permissions} from "./permissions.service";

export function canActivate(
    route: ActivatedRouteSnapshot
): Observable<boolean|UrlTree>|Promise<boolean|UrlTree>|boolean|UrlTree {
    const permissions = inject(Permissions);
    const currentUser = inject(UserToken);
    const requiredRole = route.data['requiredRole'];
    if(!requiredRole) {
        return true;
    }
    return permissions.canActivate(currentUser, requiredRole);
}
