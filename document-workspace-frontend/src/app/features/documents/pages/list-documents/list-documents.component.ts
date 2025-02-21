import {Component, OnInit} from '@angular/core';
import {DocumentService} from '../../services/document.service';
import {DocumentInfo} from '../../model/document-info';
import {NgForOf} from '@angular/common';
import {RouterLink} from '@angular/router';
import {MatMenuModule} from '@angular/material/menu';
import {MatIconButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';

@Component({
  selector: 'app-list-documents',
  standalone: true,
  imports: [
    NgForOf,
    RouterLink,
    MatMenuModule,
    MatIconButton,
    MatIcon
  ],
  templateUrl: './list-documents.component.html'
})
export class ListDocumentsComponent implements OnInit {



  documents: DocumentInfo[] = [];

  constructor(private documentService: DocumentService) {

  }

  deleteDocument(documentId: number): void {
    console.log(documentId)
    this.documentService.deleteDocument(documentId).subscribe({
      next: () => {
        this.documents = this.documents.filter(doc => doc.id !== documentId);
      },
      error: err => {
        console.log(err)
      }
    })

  }

  ngOnInit(): void {
    this.documentService.getDocuments().subscribe({
      next: documents => {
        this.documents = documents;
      },
      error: err => {
        console.log(err)
      }
    })
  }

}
