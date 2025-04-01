import {Routes} from '@angular/router';
import {LoginComponent} from './features/auth/pages/login/login.component';

export const routes: Routes = [
  { path: 'documents', loadChildren: () => import('./features/documents/documents.module').then(m => m.DocumentsModule) },
  { path: 'folder', loadChildren: () => import('./features/folder/folder.module').then(m => m.FolderModule) },
  { path: 'login', component: LoginComponent },
  { path: '**', redirectTo: 'folder/1' }
];
