import {HttpInterceptorFn} from '@angular/common/http';
import {AuthenticationService} from '../services/authentication.service';
import {inject} from '@angular/core';

export const authenticationInterceptor: HttpInterceptorFn = (req, next) => {


  const excludedRoutes: string[] = ['/authenticate', 'register'];

  if (excludedRoutes.some(route => req.url.includes(route))) {
    return next(req);
  }

  const authenticationService: AuthenticationService = inject(AuthenticationService)
  const token = authenticationService.getToken();

  if (token) {
    const clonedRequest = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
    return next(clonedRequest);
  }

  return next(req);
};
