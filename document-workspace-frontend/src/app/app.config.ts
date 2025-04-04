import {ApplicationConfig, provideZoneChangeDetection} from '@angular/core';
import {provideRouter} from '@angular/router';

import {routes} from './app.routes';
import {DocumentEditorModule} from '@onlyoffice/document-editor-angular';
import {provideHttpClient, withInterceptors} from '@angular/common/http';
import {provideAnimationsAsync} from '@angular/platform-browser/animations/async';
import {SweetAlert2Module} from '@sweetalert2/ngx-sweetalert2';
import {authenticationInterceptor} from './core/interceptors/authentication.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(
      withInterceptors([authenticationInterceptor])
    ),
    provideZoneChangeDetection({ eventCoalescing: true }),
    DocumentEditorModule,
    provideAnimationsAsync(),
    SweetAlert2Module
  ]
};
