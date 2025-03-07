import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ViewFolderComponent} from '../folder/view-folder/view-folder.component';

const routes: Routes = [
  { path: ':id', component: ViewFolderComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DocumentsRoutingModule { }
