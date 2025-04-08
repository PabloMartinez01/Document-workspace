import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MatIcon} from '@angular/material/icon';
import {MatIconButton} from '@angular/material/button';
import {MatMenu, MatMenuItem, MatMenuTrigger} from '@angular/material/menu';
import {NgForOf, NgStyle} from '@angular/common';
import {Folder} from '../../../../core/model/folder/folder';
import {RouterLink} from '@angular/router';
import {ExtensionService} from '../../../../core/services/extension.service';
import {environment} from '../../../../../environments/environment';
import {WebSocketService} from '../../../../core/services/web-socket.service';
import {Type} from '../../../../core/model/type.enum';
import {FolderDocumentMenuComponent} from '../folder-document-menu/folder-document-menu.component';

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
    RouterLink,
    NgStyle,
    FolderDocumentMenuComponent
  ],
  templateUrl: './folder-document-list.component.html'
})
export class FolderDocumentListComponent {
  readonly colors: Record<Type, string> = {
    [Type.DOCUMENT]: '#006199',
    [Type.SLIDE]: '#ffa845',
    [Type.SPREADSHEET]: '#009913',
    [Type.FORM]: '#ff4545',
  }

  @Input() folder?: Folder;
  @Output() deleteDocumentEmitter: EventEmitter<number> = new EventEmitter<number>();

  constructor(
    private extensionService: ExtensionService,
    private webSocketService: WebSocketService
  ) {
    this.initializeWebSocket();
  }

  private initializeWebSocket() {
    this.webSocketService.getDocumentLockTopic().subscribe({
      next: lockEvent => {
        if (!this.folder || !lockEvent || !lockEvent.id) return;
        this.folder.documents = this.folder.documents.map(document =>
          document.id === lockEvent.id ? {...document, locked: lockEvent.lock} : document
        );
      },
      error: err => console.log(err)
    })
  }

  getType(extension: string): string {
    const type = this.extensionService.getType(extension);
    if (type === null) return "gray";
    return this.colors[type];
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

  protected readonly environment = environment;
}
