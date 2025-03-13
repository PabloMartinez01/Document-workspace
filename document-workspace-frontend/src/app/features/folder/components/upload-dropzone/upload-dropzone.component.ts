import {Component, EventEmitter, Output} from '@angular/core';
import {NgIf} from '@angular/common';
import {NgxDropzoneModule} from 'ngx-dropzone';

@Component({
  selector: 'app-upload-dropzone',
  standalone: true,
  imports: [
    NgIf,
    NgxDropzoneModule
  ],
  templateUrl: './upload-dropzone.component.html'
})
export class UploadDropzoneComponent {

  file: File | null = null;
  @Output() uploadFileEmitter: EventEmitter<File> = new EventEmitter<File>();

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

      this.uploadFileEmitter.emit(clonedFile);
      this.file = null;
    }
  }

}
