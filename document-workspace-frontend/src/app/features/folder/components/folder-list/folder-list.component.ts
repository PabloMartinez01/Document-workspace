import {Component, Input} from '@angular/core';
import {NgForOf} from '@angular/common';
import {RouterLink} from '@angular/router';
import {Folder} from '../../../../core/model/folder/folder';

@Component({
  selector: 'folder-list',
  standalone: true,
  imports: [
    NgForOf,
    RouterLink
  ],
  templateUrl: './folder-list.component.html'
})
export class FolderListComponent {

  @Input() folder?: Folder;

}
