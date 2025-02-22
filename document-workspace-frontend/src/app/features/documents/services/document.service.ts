import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../../environments/environment';
import {DocumentInfo} from '../model/document-info';

@Injectable({
  providedIn: 'root'
})
export class DocumentService {

  constructor(private http: HttpClient) {}

  getDocuments(): Observable<DocumentInfo[]> {
    return this.http.get<DocumentInfo[]>(environment.documentService + "/document");
  }

  deleteDocument(id: number): Observable<any> {
    return this.http.delete(environment.documentService + "/document/" + id)
  }

  uploadDocument(formData: FormData): Observable<DocumentInfo> {
    return this.http.post<DocumentInfo>(environment.documentService  + '/document', formData);
  }

}
