import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {MatIcon} from '@angular/material/icon';
import {MatIconButton} from '@angular/material/button';
import {MatMenu, MatMenuItem, MatMenuTrigger} from '@angular/material/menu';
import {Action} from '../../../../core/model/action.enum';
import {RouterLink} from '@angular/router';
import {ExtensionService} from '../../../../core/services/extension.service';
import {DocumentInfoResponse} from '../../../../core/model/document/document-info-response';
import {environment} from '../../../../../environments/environment';
import {ExtensionResponse} from '../../../../core/model/extension/extension-response';

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
export class FolderDocumentMenuComponent implements OnInit {
  readonly downloadUrl: string = `${environment.documentService}/document/`;

  @Output() deleteDocumentEmitter: EventEmitter<number> = new EventEmitter<number>();
  @Input() document!: DocumentInfoResponse;

  isEditable: boolean = false;
  isViewable: boolean = false;

  constructor(private extensionService: ExtensionService) {

  }

  ngOnInit(): void {
    this.extensionService.findExtensionByName(this.document.extension).subscribe({
      next: (extensionResponse: ExtensionResponse) => {
        this.isEditable = extensionResponse.actions.includes('edit');
        this.isViewable = extensionResponse.actions.includes('view');
      },
      error: _ => {
        this.isEditable = false;
        this.isViewable = false;
      }
    })
  }

  deleteDocument(id: number): void {
    this.deleteDocumentEmitter.emit(id);
  }

  protected readonly Action = Action;
}
