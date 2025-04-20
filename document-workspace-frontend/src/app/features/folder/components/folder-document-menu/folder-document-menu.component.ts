import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MatIcon} from '@angular/material/icon';
import {MatIconButton} from '@angular/material/button';
import {MatMenu, MatMenuItem, MatMenuTrigger} from '@angular/material/menu';
import {Action} from '../../../../core/model/action.enum';
import {RouterLink} from '@angular/router';
import {DocumentResponse} from '../../../../core/model/document/document-response';
import {environment} from '../../../../../environments/environment';

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

  @Output() deleteDocumentEmitter: EventEmitter<number> = new EventEmitter<number>();
  @Input() document!: DocumentResponse;

  deleteDocument(id: number): void {
    this.deleteDocumentEmitter.emit(id);
  }

  protected readonly Action = Action;

  isViewable() {
    return this.document.extension.actions.includes('view');
  }

  isEditable() {
    return this.document.extension.actions.includes('edit');
  }

}
