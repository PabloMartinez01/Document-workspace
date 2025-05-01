import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {DocumentResponse} from '../model/document/document-response';
import {DocumentFilterRequest} from '../model/document/document-filter-request';

@Injectable({
  providedIn: 'root'
})
export class DocumentService {

  constructor(private http: HttpClient) {}

  getDocuments(): Observable<DocumentResponse[]> {
    return this.http.get<DocumentResponse[]>(`${environment.documentService}/document`);
  }

  deleteDocument(id: number): Observable<any> {
    return this.http.delete(`${environment.documentService}/document/${id}`)
  }

  uploadDocument(formData: FormData): Observable<DocumentResponse> {
    return this.http.post<DocumentResponse>(`${environment.documentService}/document`, formData);
  }

  getDocumentsByFilter(filter: DocumentFilterRequest) {
    return this.http.post<DocumentResponse[]>(`${environment.documentService}/document/filter`, filter)
  }

}
