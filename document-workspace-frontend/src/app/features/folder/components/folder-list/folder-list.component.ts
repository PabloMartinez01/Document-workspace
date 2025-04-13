import {Component, Input} from '@angular/core';
import {NgClass, NgForOf} from '@angular/common';
import {RouterLink} from '@angular/router';
import {Folder} from '../../../../core/model/folder/folder';
import {MatIcon} from "@angular/material/icon";
import {MatIconButton} from '@angular/material/button';
import {FormsModule} from '@angular/forms';
import {MatMenu, MatMenuItem, MatMenuTrigger} from '@angular/material/menu';

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
    NgClass
  ],
  templateUrl: './folder-list.component.html'
})
export class FolderListComponent {

  @Input() folder!: Folder;
  grid: boolean = true;

  toggleGrid() {
    this.grid = !this.grid;
  }
}
