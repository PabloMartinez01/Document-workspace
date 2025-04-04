import {CanActivateFn, Router} from '@angular/router';
import {AuthenticationService} from '../services/authentication.service';
import {inject} from '@angular/core';

export const authGuard: CanActivateFn = (route, state) => {


  const authenticationService: AuthenticationService = inject(AuthenticationService);
  const router: Router = inject(Router);

  if (authenticationService.isAuthenticated()){
    return true;
  }

  router.navigate(['/login']).then();
  return false;

};
