import {Routes} from '@angular/router';
import {LoginComponent} from './features/auth/pages/login/login.component';
import {authGuard} from './core/guard/auth.guard';

export const routes: Routes = [
  {
    path: 'documents',
    loadChildren: () => import('./features/documents/documents.module').then(m => m.DocumentsModule),
    canActivate: [authGuard]
  },
  {
    path: 'folder',
    loadChildren: () => import('./features/folder/folder.module').then(m => m.FolderModule),
    canActivate: [authGuard]
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: '**',
    redirectTo: 'folder/1'
  }
];
