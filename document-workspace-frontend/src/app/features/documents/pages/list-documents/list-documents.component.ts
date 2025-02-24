import {Component, OnInit} from '@angular/core';
import {DocumentService} from '../../services/document.service';
import {DocumentInfo} from '../../model/document-info';
import {NgForOf, NgIf} from '@angular/common';
import {RouterLink} from '@angular/router';
import {MatMenuModule} from '@angular/material/menu';
import {MatIconButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import {UploadDropzoneComponent} from '../../components/upload-dropzone/upload-dropzone.component';
import {ExtensionService} from '../../services/extension.service';
import {AlertService} from '../../services/alert.service';

@Component({
  selector: 'app-list-documents',
  standalone: true,
  imports: [
    NgForOf,
    RouterLink,
    MatMenuModule,
    MatIconButton,
    MatIcon,
    UploadDropzoneComponent,
    NgIf
  ],
  templateUrl: './list-documents.component.html'
})
export class ListDocumentsComponent implements OnInit {

  documents: DocumentInfo[] = [];

  constructor(
    private extensionService: ExtensionService,
    private documentService: DocumentService,
    private alertService: AlertService ) {

  }

  ngOnInit(): void {
    this.documentService.getDocuments().subscribe({
      next: documents => this.documents = documents,
      error: err => console.log(err)
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

  uploadDocument(file: File): void {
    if (!file) return;

    const formData = new FormData();
    formData.append('file', file);

    this.documentService.uploadDocument(formData).subscribe({
      next: (documentInfo) => {
        this.documents = [...this.documents, documentInfo]
        this.alertService.showSuccessAlert('File uploaded', 'The file has been uploaded successfully').then();
      },
      error: () => this.alertService.showErrorAlert('Upload error', 'The file was not uploaded successfully').then()
    });
  }


  deleteDocument(documentId: number): void {
    this.alertService.showConfirmationAlert(() => {
      this.documentService.deleteDocument(documentId).subscribe({
        next: () => {
          this.documents = this.documents.filter(doc => doc.id !== documentId);
          this.alertService.showSuccessAlert('Deleted!', 'The file has been deleted successfully').then();
        },
        error: () => this.alertService.showErrorAlert('Error', 'The file was not deleted successfully').then()
      });
    })

  }



}
