import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ViewDocumentComponent} from './pages/view-document/view-document.component';

const routes: Routes = [
  { path: ':id', component: ViewDocumentComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DocumentsRoutingModule { }
