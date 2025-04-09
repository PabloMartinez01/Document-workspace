import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ViewFolderComponent} from './pages/view-folder/view-folder.component';
import {authGuard} from '../../core/guard/auth.guard';


const routes: Routes = [
  {
    path: ':id',
    component: ViewFolderComponent,
    canActivate: [authGuard],
    runGuardsAndResolvers: 'paramsChange'
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FolderRoutingModule { }
