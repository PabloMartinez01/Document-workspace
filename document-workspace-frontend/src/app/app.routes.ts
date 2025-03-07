import {Routes} from '@angular/router';

export const routes: Routes = [
  { path: 'documents', loadChildren: () => import('./features/documents/documents.module').then(m => m.DocumentsModule) },
  { path: 'folder', loadChildren: () => import('./features/folder/folder.module').then(m => m.FolderModule) },
  { path: '**', redirectTo: 'folder/1' }
];
