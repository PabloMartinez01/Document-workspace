import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListDocumentsComponent} from './pages/list-documents/list-documents.component';
import {ViewDocumentComponent} from './pages/view-document/view-document.component';

const routes: Routes = [
  { path: '', component: ListDocumentsComponent },
  { path: ':id', component: ViewDocumentComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DocumentsRoutingModule { }
