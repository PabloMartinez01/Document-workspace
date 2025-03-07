import {Component, OnInit} from '@angular/core';
import {FolderService} from '../../../core/services/folder.service';
import {Folder} from '../../../core/model/folder';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {MatIcon} from '@angular/material/icon';
import {MatIconButton} from '@angular/material/button';
import {MatMenu, MatMenuItem, MatMenuTrigger} from '@angular/material/menu';
import {NgForOf} from '@angular/common';
import {UploadDropzoneComponent} from '../../documents/components/upload-dropzone/upload-dropzone.component';
import {Action} from '../../../core/model/action.enum';
import {ExtensionService} from '../../../core/services/extension.service';
import {AlertService} from '../../../core/services/alert.service';
import {DocumentService} from '../../../core/services/document.service';

@Component({
  selector: 'app-view-folder',
  standalone: true,
  imports: [
    MatIcon,
    MatIconButton,
    MatMenu,
    MatMenuItem,
    NgForOf,
    UploadDropzoneComponent,
    RouterLink,
    MatMenuTrigger

  ],
  templateUrl: './view-folder.component.html'
})
export class ViewFolderComponent implements OnInit {

    folder?: Folder;
    id: number | null = null;

    constructor(
      private folderService: FolderService,
      private extensionService: ExtensionService,
      private alertService: AlertService,
      private documentService: DocumentService,
      private activatedRoute: ActivatedRoute,
      private router: Router
    ) {

    }

    ngOnInit(): void {
      this.activatedRoute.paramMap.subscribe(params => {
        const id = params.get('id');
        this.id = id ? Number(id) : null;

        if (this.id !== null) {
          this.folderService.getFolder(this.id).subscribe({
            next: folder => this.folder = folder,
            error: err => console.log(err)
          });
        }
      })
    }

  isEditable(extension: string): boolean {
    return this.extensionService.isEditable(extension);
  }

  isViewable(extension: string): boolean {
    return this.extensionService.isViewable(extension);
  }

  isSupported(extension: string): boolean {
    return this.extensionService.isSupported(extension);
  }

  deleteDocument(documentId: number): void {
    this.alertService.showConfirmationAlert(() => {
      this.documentService.deleteDocument(documentId).subscribe({
        next: () => {
          if (!this.folder) return;
          this.folder.documents = this.folder.documents.filter(doc => doc.id !== documentId);
          this.alertService.showSuccessAlert('Deleted!', 'The file has been deleted successfully').then();
        },
        error: () => this.alertService.showErrorAlert('Error', 'The file was not deleted successfully').then()
      });
    })

  }


  protected readonly Action = Action;
}
