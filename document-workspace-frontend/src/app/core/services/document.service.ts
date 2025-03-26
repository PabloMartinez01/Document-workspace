import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {DocumentInfoResponse} from '../model/document/document-info-response';

@Injectable({
  providedIn: 'root'
})
export class DocumentService {

  constructor(private http: HttpClient) {}

  getDocuments(): Observable<DocumentInfoResponse[]> {
    return this.http.get<DocumentInfoResponse[]>(environment.documentService + "/document");
  }

  deleteDocument(id: number): Observable<any> {
    return this.http.delete(environment.documentService + "/document/" + id)
  }

  uploadDocument(formData: FormData): Observable<DocumentInfoResponse> {
    return this.http.post<DocumentInfoResponse>(environment.documentService  + '/document', formData);
  }

}
