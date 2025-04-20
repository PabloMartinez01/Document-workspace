import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MatIcon} from '@angular/material/icon';
import {MatIconButton} from '@angular/material/button';
import {MatMenu, MatMenuItem, MatMenuTrigger} from '@angular/material/menu';
import {DatePipe, NgClass, NgForOf, NgStyle} from '@angular/common';
import {RouterLink} from '@angular/router';
import {environment} from '../../../../../environments/environment';
import {WebSocketService} from '../../../../core/services/web-socket.service';
import {Type} from '../../../../core/model/type.enum';
import {FolderDocumentMenuComponent} from '../folder-document-menu/folder-document-menu.component';
import {MatChipGrid, MatChipOption} from '@angular/material/chips';
import {UploadDropzoneComponent} from '../upload-dropzone/upload-dropzone.component';
import {DocumentResponse} from '../../../../core/model/document/document-response';
import {debounceTime, filter, Subject, switchMap} from 'rxjs';
import {FormsModule} from '@angular/forms';
import {DocumentService} from '../../../../core/services/document.service';
import {DocumentFilterRequest} from '../../../../core/model/document/document-filter-request';

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
    DatePipe,
  ],
  templateUrl: './folder-document-list.component.html',
})
export class FolderDocumentListComponent {

  readonly colors: Record<Type, string> = {
    [Type.WORD]: '#006199',
    [Type.SLIDE]: '#ffa845',
    [Type.CELL]: '#009913',
    [Type.PDF]: '#ff4545',
    [Type.OTHER]: '#949494'
  };

  searchTypeFilters = [
    { type: Type.WORD, label: 'Document', activated: true },
    { type: Type.SLIDE, label: 'Slide', activated: true },
    { type: Type.CELL, label: 'Spreadsheet', activated: true },
    { type: Type.PDF, label: 'Form', activated: true },
    { type: Type.OTHER, label: 'Other', activated: true },
  ];

  @Input() folderId!: number;
  @Input() documents!: DocumentResponse[];

  @Output() deleteDocumentEmitter: EventEmitter<number> = new EventEmitter<number>();

  private searchSubject: Subject<DocumentFilterRequest> = new Subject<DocumentFilterRequest>();
  search: string = '';

  constructor(
    private webSocketService: WebSocketService,
    private documentService: DocumentService
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
      switchMap(documentFilterRequest => this.documentService.getDocumentsByFilter(documentFilterRequest))
    ).subscribe({
      next: documents => this.documents = documents,
      error: err => console.log(err)
    })
  }


  bytesToString(bytes: number): string {
    if (bytes < 1024) return `${bytes} bytes`;
    if (bytes < 1024 * 1024) return `${(bytes / 1024).toFixed(2)} KB`;
    if (bytes < 1024 * 1024 * 1024) return `${(bytes / (1024 * 1024)).toFixed(2)} MB`;
    return `${(bytes / (1024 * 1024 * 1024)).toFixed(2)} GB`;
  }

  onSearchChange() {
    this.searchSubject.next({
      folderId: this.folderId,
      filename: this.search || '',
      types: this.searchTypeFilters
        .filter(e => e.activated)
        .map(e => e.type.valueOf().toLowerCase())
    })
  }

  protected readonly environment = environment;
}
