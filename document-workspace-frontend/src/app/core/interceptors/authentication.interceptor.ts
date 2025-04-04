import {HttpInterceptorFn} from '@angular/common/http';
import {AuthenticationService} from '../services/authentication.service';
import {inject} from '@angular/core';

export const authenticationInterceptor: HttpInterceptorFn = (req, next) => {

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
