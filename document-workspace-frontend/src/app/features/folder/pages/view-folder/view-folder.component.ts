import {Component, HostListener, OnInit} from '@angular/core';
import {FolderService} from '../../../../core/services/folder.service';
import {Folder} from '../../../../core/model/folder/folder';
import {ActivatedRoute, RouterLink, RouterOutlet} from '@angular/router';
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
import {SidebarComponent} from '../../../../shared/sidebar/sidebar.component';
import {ErrorResponse} from '../../../../core/model/error/error-response';
import {HttpErrorResponse} from '@angular/common/http';
import {DeviceDetectorService} from 'ngx-device-detector';

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
    SidebarComponent,
    RouterOutlet,
  ],
  templateUrl: './view-folder.component.html',
})
export class ViewFolderComponent implements OnInit {
  folder!: Folder;

  private touchStartX = 0;
  private touchEndX = 0;
  private ignoreSwipe = false;

  sidebarHidden = false;

  constructor(
    private folderService: FolderService,
    private alertService: AlertService,
    private documentService: DocumentService,
    private activatedRoute: ActivatedRoute,
    private deviceService: DeviceDetectorService,
  ) {}

  ngOnInit(): void {
    this.initializeParamMapSubscription();
  }

  private initializeParamMapSubscription() {
    this.activatedRoute.paramMap.subscribe((params) => {
      const id = Number(params.get('id'));
      if (id) {
        this.folderService.getFolder(id).subscribe({
          next: (folder) => (this.folder = folder),
          error: () =>
            this.alertService.showErrorAlert(
              Messages.folderNotFound.title,
              Messages.folderNotFound.body,
            ),
        });
      } else {
        this.alertService.showErrorAlert(
          Messages.folderNotFound.title,
          Messages.folderNotFound.body,
        );
      }
    });
  }

  deleteDocument(documentId: number): void {
    this.alertService.showDefaultConfirmationAlert(() => {
      this.documentService.deleteDocument(documentId).subscribe({
        next: () => {
          this.folder.documents = this.folder.documents.filter(
            (doc) => doc.id !== documentId,
          );
          this.alertService.showSuccessAlert(
            Messages.deleteSuccess.title,
            Messages.deleteSuccess.body,
          );
        },
        error: () =>
          this.alertService.showErrorAlert(
            Messages.deleteError.title,
            Messages.deleteError.body,
          ),
      });
    });
  }

  uploadDocument(file: File): void {
    if (!file) return;

    const formData: FormData = new FormData();
    formData.append('file', file);
    formData.append('folderId', this.folder.id.toString());

    this.documentService.uploadDocument(formData).subscribe({
      next: (documentInfo) => {
        this.folder.documents = [...this.folder.documents, documentInfo];
        this.alertService.showSuccessAlert(
          Messages.uploadSuccess.title,
          Messages.uploadSuccess.body,
        );
      },
      error: () =>
        this.alertService.showErrorAlert(
          Messages.uploadError.title,
          Messages.uploadError.body,
        ),
    });
  }

  createFolder(folderName: string): void {
    this.folderService
      .createFolder({ name: folderName, parentFolderId: this.folder.id })
      .subscribe({
        next: (folderInfo: FolderInfoResponse) => {
          this.folder.folders = [...this.folder.folders, folderInfo];
          this.alertService.showSuccessAlert(
            Messages.createFolderSuccess.title,
            Messages.createFolderSuccess.body,
          );
        },
        error: (error: HttpErrorResponse) => {
          try {
            const errorResponse: ErrorResponse = error.error as ErrorResponse;
            if (errorResponse?.errors?.[0]) {
              this.alertService.showErrorAlert(
                Messages.createFolderError.title,
                errorResponse?.errors?.[0],
              );
              return;
            }
          } catch (e) {}

          this.alertService.showErrorAlert(
            Messages.createFolderError.title,
            Messages.createFolderError.body,
          );
        },
      });
  }

  deleteFolder(folderId: number) {

    this.alertService.showConfirmationAlert(
      Messages.deleteFolderConfirmation.title,
      Messages.deleteFolderConfirmation.body,
      () => {
        this.folderService.deleteFolder(folderId).subscribe({
          next: () => {
            this.folder.folders = this.folder.folders.filter(folder => folder.id !== folderId);
            this.alertService.showSuccessAlert(
              Messages.deleteFolderSuccess.title,
              Messages.deleteFolderSuccess.body
            )
          },
          error: err => {
            this.alertService.showErrorAlert(
              Messages.deleteFolderError.title,
              Messages.deleteFolderError.body
            )
          }
        })
      }
    )
  }

  @HostListener('touchstart', ['$event'])
  onTouchStart(event: TouchEvent) {
    if (!this.isMobile()) return;

    this.ignoreSwipe = false;
    this.touchStartX = event.changedTouches[0].screenX;
  }

  @HostListener('touchend', ['$event'])
  onTouchEnd(event: TouchEvent) {
    if (!this.isMobile()) return;
    if (this.ignoreSwipe) return;

    this.touchEndX = event.changedTouches[0].screenX;
    this.handleSwipeGesture();
  }

  handleSwipeGesture() {
    const swipeDistance = this.touchEndX - this.touchStartX;
    if (swipeDistance > 150) {
      this.sidebarHidden = false;
    } else if (swipeDistance < -50) {
      this.sidebarHidden = true;
    }
  }

  isMobile(): boolean {
    return this.deviceService.isMobile();
  }

  showSidebar() {
    this.sidebarHidden = false;
  }


  protected readonly document = document;

}
