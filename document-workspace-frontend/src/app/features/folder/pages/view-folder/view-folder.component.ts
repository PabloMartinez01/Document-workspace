import {Component, OnInit} from '@angular/core';
import {FolderService} from '../../../../core/services/folder.service';
import {Folder} from '../../../../core/model/folder/folder';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {MatIcon} from '@angular/material/icon';
import {MatIconButton} from '@angular/material/button';
import {MatMenu, MatMenuItem, MatMenuTrigger} from '@angular/material/menu';
import {NgForOf, NgIf} from '@angular/common';
import {UploadDropzoneComponent} from '../../components/upload-dropzone/upload-dropzone.component';
import {AlertService} from '../../../../core/services/alert.service';
import {DocumentService} from '../../../../core/services/document.service';
import {Messages} from '../../../../core/model/messages/messages';
import {FolderInfoResponse} from '../../../../core/model/folder/folder-info-response';
import {FormsModule} from '@angular/forms';
import {FolderInfoComponent} from '../../components/folder-info/folder-info.component';
import {FolderListComponent} from '../../components/folder-list/folder-list.component';
import {FolderDocumentListComponent} from '../../components/folder-document-list/folder-document-list.component';
import {FolderToolbarComponent} from '../../components/folder-toolbar/folder-toolbar.component';
import {SidebarComponent} from '../../../../shared/sidebar/sidebar.component';

@Component({
  selector: 'view-folder',
  standalone: true,
  imports: [
    MatIcon,
    MatIconButton,
    MatMenu,
    MatMenuItem,
    NgForOf,
    UploadDropzoneComponent,
    RouterLink,
    MatMenuTrigger,
    NgIf,
    FormsModule,
    FolderInfoComponent,
    FolderListComponent,
    FolderDocumentListComponent,
    FolderToolbarComponent,
    SidebarComponent

  ],
  templateUrl: './view-folder.component.html'
})
export class ViewFolderComponent implements OnInit {

  folder?: Folder;

  constructor(
    private folderService: FolderService,
    private alertService: AlertService,
    private documentService: DocumentService,
    private activatedRoute: ActivatedRoute
  ) {

  }

  ngOnInit(): void {
    this.initializeParamMapSubscription();
  }


  private initializeParamMapSubscription() {
    this.activatedRoute.paramMap.subscribe(params => {
      const id = Number(params.get('id'));
      if (id) {
        this.folderService.getFolder(id).subscribe({
          next: folder => this.folder = folder,
          error: err => {
            this.alertService.showErrorAlert(Messages.folderNotFound.title, Messages.folderNotFound.body)
          }
        });
      } else {
        this.alertService.showErrorAlert(Messages.folderNotFound.title, Messages.folderNotFound.body)
      }
    })
  }



  deleteDocument(documentId: number): void {
    this.alertService.showConfirmationAlert(() => {
      this.documentService.deleteDocument(documentId).subscribe({
        next: () => {
          if (!this.folder) return;
          this.folder.documents = this.folder.documents.filter(doc => doc.id !== documentId);
          this.alertService.showSuccessAlert(Messages.deleteSuccess.title, Messages.deleteSuccess.body);
        },
        error: () => this.alertService.showErrorAlert(Messages.deleteError.title, Messages.deleteError.body)
      });
    })
  }

  uploadDocument(file: File): void {

    if (!(file && this.folder)) return;

    const formData: FormData = new FormData();
    formData.append('file', file);
    formData.append('folderId', this.folder.id.toString())

    this.documentService.uploadDocument(formData).subscribe({
      next: (documentInfo) => {
        if (!this.folder) return;
        this.folder.documents = [...this.folder.documents, documentInfo];
        this.alertService.showSuccessAlert(Messages.uploadSuccess.title, Messages.uploadSuccess.body);
      },
      error: () => this.alertService.showErrorAlert(Messages.uploadError.title, Messages.uploadError.body)
    });
  }

  createFolder(folderName: string): void {

    if (!this.folder) return;

    this.folderService.createFolder({name: folderName, parentFolderId: this.folder.id}).subscribe({
      next: (folderInfo: FolderInfoResponse) => {
        if (!this.folder) return;
        this.folder.folders = [...this.folder.folders, folderInfo];
        this.alertService.showSuccessAlert(Messages.createFolderSuccess.title, Messages.createFolderSuccess.body);
      },
      error: () => this.alertService.showErrorAlert(Messages.createFolderError.title, Messages.createFolderError.body)
    })

  }

}
