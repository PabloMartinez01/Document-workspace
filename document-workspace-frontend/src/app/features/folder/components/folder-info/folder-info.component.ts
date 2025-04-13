import {Component, Input} from '@angular/core';
import {MatIconButton} from "@angular/material/button";
import {RouterLink} from '@angular/router';
import {Folder} from '../../../../core/model/folder/folder';
import {MatIcon} from '@angular/material/icon';

@Component({
  selector: 'folder-info',
  standalone: true,
  imports: [
    MatIconButton,
    RouterLink,
    MatIcon
  ],
  templateUrl: './folder-info.component.html'
})
export class FolderInfoComponent {

  @Input() folder?: Folder;

}
