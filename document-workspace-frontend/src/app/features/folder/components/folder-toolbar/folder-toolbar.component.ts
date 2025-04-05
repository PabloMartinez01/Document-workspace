import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {MatIconButton} from '@angular/material/button';
import {MatMenu, MatMenuTrigger} from '@angular/material/menu';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Folder} from '../../../../core/model/folder/folder';
import {debounceTime, filter, Subject, switchMap} from 'rxjs';
import {FolderService} from '../../../../core/services/folder.service';

@Component({
  selector: 'folder-toolbar',
  standalone: true,
  imports: [
    MatIconButton,
    MatMenu,
    ReactiveFormsModule,
    FormsModule,
    MatMenuTrigger
  ],
  templateUrl: './folder-toolbar.component.html'
})
export class FolderToolbarComponent implements OnInit {

  @Input() folder?: Folder;
  @Output() createFolderEmitter: EventEmitter<string> = new EventEmitter<string>();
  private searchSubject: Subject<string> = new Subject<string>();

  folderName: string = '';
  search: string = '';

  constructor(private folderService: FolderService) {
  }

  ngOnInit(): void {
    this.initializeSearchSubscription();
  }

  private initializeSearchSubscription() {
    this.searchSubject.pipe(
      debounceTime(700),
      filter(() => !!this.folder?.id),
      switchMap(searchTerm => this.folderService.getFolderItemsByName(this.folder!.id, searchTerm))
    ).subscribe({
      next: folderItems => {
        if (!this.folder) return;
        this.folder.folders = folderItems.folders;
        this.folder.documents = folderItems.documents;
      },
      error: err => console.log(err)
    })
  }

  createFolder() {
    if (!this.folderName) return;
    this.createFolderEmitter.emit(this.folderName);
    this.folderName = '';
  }


  onSearchChange() {
    this.searchSubject.next(this.search)
  }

}
