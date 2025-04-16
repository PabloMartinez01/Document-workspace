import {Component, EventEmitter, Output} from '@angular/core';
import {NgIf} from '@angular/common';
import {NgxDropzoneModule} from 'ngx-dropzone';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'upload-dropzone',
  standalone: true,
  imports: [
    NgIf,
    NgxDropzoneModule,
    MatButton
  ],
  templateUrl: './upload-dropzone.component.html'
})
export class UploadDropzoneComponent {

  file: File | null = null;
  @Output() uploadDocumentEmitter: EventEmitter<File> = new EventEmitter<File>();

  onSelect(event: any) {
    this.file = event.addedFiles[0];
  }

  onRemove() {
    this.file = null;
  }

  onUploadFile() {
    if (this.file) {

      const clonedFile: File = new File([this.file], this.file.name, {
        type: this.file.type,
        lastModified: this.file.lastModified
      })

      this.uploadDocumentEmitter.emit(clonedFile);
      this.file = null;
    }
  }

}
