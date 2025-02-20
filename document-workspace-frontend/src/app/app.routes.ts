import {Routes} from '@angular/router';

export const routes: Routes = [
  { path: 'documents', loadChildren: () => import('./features/documents/documents.module').then(m => m.DocumentsModule) },
  { path: '**', redirectTo: 'documents' }
];
