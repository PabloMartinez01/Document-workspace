import {Component, EventEmitter, Input, Output} from '@angular/core';
import {NgClass, NgForOf} from '@angular/common';
import {RouterLink} from '@angular/router';
import {MatIcon} from '@angular/material/icon';
import {MatButton, MatIconButton} from '@angular/material/button';
import {FormsModule} from '@angular/forms';
import {MatMenu, MatMenuItem, MatMenuTrigger} from '@angular/material/menu';
import {MatButtonToggle, MatButtonToggleGroup,} from '@angular/material/button-toggle';
import {FolderInfoResponse} from '../../../../core/model/folder/folder-info-response';

@Component({
  selector: 'folder-list',
  standalone: true,
  imports: [
    NgForOf,
    RouterLink,
    MatIcon,
    MatIconButton,
    FormsModule,
    MatMenu,
    MatMenuTrigger,
    MatMenuItem,
    NgClass,
    MatButtonToggleGroup,
    MatButtonToggle,
    MatButton,
  ],
  templateUrl: './folder-list.component.html',
})
export class FolderListComponent {

  @Input() subfolders!: FolderInfoResponse[];

  @Output() createFolderEmitter: EventEmitter<string> = new EventEmitter<string>();
  @Output() deleteFolderEmitter: EventEmitter<number> = new EventEmitter<number>();

  folderName: string = '';
  grid: boolean = true;

  toggleGrid() {
    this.grid = !this.grid;
  }

  createFolder() {
    this.createFolderEmitter.emit(this.folderName);
  }

  deleteFolder(id: number) {
    this.deleteFolderEmitter.emit(id);
  }

}
