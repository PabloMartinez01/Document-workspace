import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {DocumentEditorModule} from '@onlyoffice/document-editor-angular';
import {
  FolderDocumentListComponent
} from "./features/folder/components/folder-document-list/folder-document-list.component";
import {FolderInfoComponent} from "./features/folder/components/folder-info/folder-info.component";
import {FolderListComponent} from "./features/folder/components/folder-list/folder-list.component";
import {NgIf} from "@angular/common";
import {SidebarComponent} from "./shared/sidebar/sidebar.component";
import {UploadDropzoneComponent} from "./features/folder/components/upload-dropzone/upload-dropzone.component";


@Component({
  selector: 'app-root',
  standalone: true,
    imports: [RouterOutlet, DocumentEditorModule, FolderDocumentListComponent, FolderInfoComponent, FolderListComponent, NgIf, SidebarComponent, UploadDropzoneComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {


}
