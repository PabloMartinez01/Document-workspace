import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Action} from '../../../../core/model/action.enum';
import {MatIcon} from '@angular/material/icon';
import {MatIconButton} from '@angular/material/button';
import {MatMenu, MatMenuItem, MatMenuTrigger} from '@angular/material/menu';
import {NgForOf} from '@angular/common';
import {Folder} from '../../../../core/model/folder/folder';
import {RouterLink} from '@angular/router';
import {ExtensionService} from '../../../../core/services/extension.service';
import {environment} from '../../../../../environments/environment';

@Component({
  selector: 'folder-document-list',
  standalone: true,
  imports: [
    MatIcon,
    MatIconButton,
    MatMenu,
    MatMenuItem,
    NgForOf,
    MatMenuTrigger,
    RouterLink
  ],
  templateUrl: './folder-document-list.component.html'
})
export class FolderDocumentListComponent {

  @Input() folder?: Folder;
  @Output() deleteDocumentEmitter: EventEmitter<number> = new EventEmitter<number>();
  readonly downloadUrl: string = `${environment.documentService}/document/`

  constructor(private extensionService: ExtensionService) {
  }

  isEditable(extension: string): boolean {
    return this.extensionService.isEditable(extension);
  }

  isSupported(extension: string): boolean {
    return this.extensionService.isSupported(extension);
  }

  deleteDocument(id: number): void {
    this.deleteDocumentEmitter.emit(id);
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
  protected readonly environment = environment;
}
