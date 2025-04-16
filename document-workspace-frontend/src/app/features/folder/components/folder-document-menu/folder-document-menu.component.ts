import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatIcon } from '@angular/material/icon';
import { MatIconButton } from '@angular/material/button';
import { MatMenu, MatMenuItem, MatMenuTrigger } from '@angular/material/menu';
import { Action } from '../../../../core/model/action.enum';
import { RouterLink } from '@angular/router';
import { ExtensionService } from '../../../../core/services/extension.service';
import { DocumentInfoResponse } from '../../../../core/model/document/document-info-response';
import { environment } from '../../../../../environments/environment';

@Component({
  selector: 'folder-document-menu',
  standalone: true,
  imports: [
    MatIcon,
    MatIconButton,
    MatMenu,
    MatMenuItem,
    MatMenuTrigger,
    RouterLink,
  ],
  templateUrl: './folder-document-menu.component.html',
})
export class FolderDocumentMenuComponent {
  readonly downloadUrl: string = `${environment.documentService}/document/`;

  @Output() deleteDocumentEmitter: EventEmitter<number> =
    new EventEmitter<number>();
  @Input() document!: DocumentInfoResponse;

  constructor(private extensionService: ExtensionService) {}

  isEditable(extension: string): boolean {
    return this.extensionService.isEditable(extension);
  }

  isSupported(extension: string): boolean {
    return this.extensionService.isSupported(extension);
  }

  deleteDocument(id: number): void {
    this.deleteDocumentEmitter.emit(id);
  }

  protected readonly Action = Action;
}
