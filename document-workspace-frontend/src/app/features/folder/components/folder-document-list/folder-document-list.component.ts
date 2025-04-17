import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MatIcon} from '@angular/material/icon';
import {MatIconButton} from '@angular/material/button';
import {MatMenu, MatMenuItem, MatMenuTrigger} from '@angular/material/menu';
import {NgClass, NgForOf, NgStyle} from '@angular/common';
import {RouterLink} from '@angular/router';
import {ExtensionService} from '../../../../core/services/extension.service';
import {environment} from '../../../../../environments/environment';
import {WebSocketService} from '../../../../core/services/web-socket.service';
import {Type} from '../../../../core/model/type.enum';
import {FolderDocumentMenuComponent} from '../folder-document-menu/folder-document-menu.component';
import {MatChipGrid, MatChipOption} from '@angular/material/chips';
import {UploadDropzoneComponent} from '../upload-dropzone/upload-dropzone.component';
import {DocumentInfoResponse} from '../../../../core/model/document/document-info-response';
import {debounceTime, filter, Subject, switchMap} from 'rxjs';
import {FolderService} from '../../../../core/services/folder.service';
import {FormsModule} from '@angular/forms';

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
    FolderDocumentMenuComponent,
    MatChipGrid,
    MatChipOption,
    NgClass,
    UploadDropzoneComponent,
    FormsModule,
  ],
  templateUrl: './folder-document-list.component.html',
})
export class FolderDocumentListComponent {
  readonly colors: Record<Type, string> = {
    [Type.DOCUMENT]: '#006199',
    [Type.SLIDE]: '#ffa845',
    [Type.SPREADSHEET]: '#009913',
    [Type.FORM]: '#ff4545',
  };

  @Input() folderId!: number;
  @Input() documents!: DocumentInfoResponse[];

  @Output() deleteDocumentEmitter: EventEmitter<number> = new EventEmitter<number>();

  private searchSubject: Subject<string> = new Subject<string>();
  search: string = '';

  constructor(
    private extensionService: ExtensionService,
    private webSocketService: WebSocketService,
    private folderService: FolderService
  ) {
    this.initializeWebSocket();
    this.initializeSearchSubscription();
  }

  private initializeWebSocket() {
    this.webSocketService.getDocumentLockTopic().subscribe({
      next: (lockEvent) => {
        if (!lockEvent?.id) return;
        this.documents = this.documents.map((document) =>
          document.id === lockEvent.id
            ? { ...document, locked: lockEvent.lock }
            : document,
        );
      },
      error: (err) => console.log(err),
    });
  }

  private initializeSearchSubscription() {
    this.searchSubject.pipe(
      debounceTime(700),
      filter(() => !!this.folderId),
      switchMap(searchTerm => this.folderService.getFolderItemsByName(this.folderId, searchTerm))
    ).subscribe({
      next: folderItems => {
        this.documents = folderItems.documents;
      },
      error: err => console.log(err)
    })
  }

  getType(extension: string): string {
    const type = this.extensionService.getType(extension);
    if (type === null) return 'gray';
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

  onSearchChange() {
    this.searchSubject.next(this.search)
  }

  protected readonly environment = environment;
}
