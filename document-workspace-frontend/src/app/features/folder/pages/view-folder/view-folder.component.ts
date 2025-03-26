import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FolderService} from '../../../../core/services/folder.service';
import {Folder} from '../../../../core/model/folder/folder';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {MatIcon} from '@angular/material/icon';
import {MatIconButton} from '@angular/material/button';
import {MatMenu, MatMenuItem, MatMenuTrigger} from '@angular/material/menu';
import {NgForOf, NgIf} from '@angular/common';
import {UploadDropzoneComponent} from '../../components/upload-dropzone/upload-dropzone.component';
import {ExtensionService} from '../../../../core/services/extension.service';
import {AlertService} from '../../../../core/services/alert.service';
import {DocumentService} from '../../../../core/services/document.service';
import {Action} from '../../../../core/model/action.enum';
import {Messages} from '../../../../core/model/messages/messages';
import {FolderInfoResponse} from '../../../../core/model/folder/folder-info-response';
import {debounceTime, filter, Subject, switchMap} from 'rxjs';
import {FormsModule} from '@angular/forms';

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
    MatMenuTrigger,
    NgIf,
    FormsModule

  ],
  templateUrl: './view-folder.component.html'
})
export class ViewFolderComponent implements OnInit {

    @ViewChild('inputFolder') inputFolder: ElementRef<HTMLInputElement>  | undefined;
    folder?: Folder;

    search: string = '';
    private searchSubject: Subject<string> = new Subject<string>();

    constructor(
      private folderService: FolderService,
      private extensionService: ExtensionService,
      private alertService: AlertService,
      private documentService: DocumentService,
      private activatedRoute: ActivatedRoute,
    ) {

    }

    ngOnInit(): void {
      this.initializeParamMapSubscription();
      this.initializeSearchSubscription();
    }

  private initializeSearchSubscription() {
    this.searchSubject.pipe(
      debounceTime(700),
      filter(() => !!this.folder?.id),
      switchMap(searchTerm => this.folderService.getFolderItemsByName(this.folder!.id, searchTerm))
    ).subscribe({
      next: folderItems => {
        if (!this.folder) return;
        this.folder.folders = folderItems.folders;
        this.folder.documents = folderItems.documents;
      },
      error: err => console.log(err)
    })
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
      }
      else {
        this.alertService.showErrorAlert(Messages.folderNotFound.title, Messages.folderNotFound.body)
      }
    })
  }

  isEditable(extension: string): boolean {
    return this.extensionService.isEditable(extension);
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
        this.folder.documents = [... this.folder.documents, documentInfo];
        this.alertService.showSuccessAlert(Messages.uploadSuccess.title, Messages.uploadSuccess.body);
      },
      error: () => this.alertService.showErrorAlert(Messages.uploadError.title, Messages.uploadError.body)
    });
  }

  createFolder() {
      if (!this.inputFolder || !this.folder) return;
      const folderName: string = this.inputFolder.nativeElement.value;

      if (!folderName) return;
      this.inputFolder.nativeElement.value = '';

      this.folderService.createFolder({name: folderName, parentFolderId: this.folder.id}).subscribe({
        next: (folderInfo: FolderInfoResponse) => {
          if (!this.folder) return;
          this.folder.folders = [...this.folder.folders, folderInfo];
          this.alertService.showSuccessAlert(Messages.createFolderSuccess.title, Messages.createFolderSuccess.body);
        },
        error: () => this.alertService.showErrorAlert(Messages.createFolderError.title, Messages.createFolderError.body)
      })

  }

  bytesToString(bytes: number): string {
    if (bytes < 1024) {
      return `${bytes} bytes`;
    }
    if (bytes < 1024 * 1024) {
      return `${(bytes / 1024).toFixed(2)} KB`;
    }
    if (bytes < 1024 * 1024 * 1024) {
      return `${(bytes / (1024 * 1024)).toFixed(2)} MB`;
    }

    return `${(bytes / (1024 * 1024 * 1024)).toFixed(2)} GB`;
  }

  protected readonly Action = Action;

  onSearchChange() {
    this.searchSubject.next(this.search)
  }


}
