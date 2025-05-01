import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {Folder} from '../model/folder/folder';
import {FolderRequest} from '../model/folder/folder-request';
import {FolderInfoResponse} from '../model/folder/folder-info-response';

@Injectable({
  providedIn: 'root'
})
export class FolderService {

  constructor(private http: HttpClient) {}

  getFolder(folderId: number): Observable<Folder> {
    return this.http.get<Folder>(`${environment.documentService}/folder/${folderId}`);
  }

  createFolder(folderRequest: FolderRequest): Observable<FolderInfoResponse> {
    return this.http.post<FolderInfoResponse>(`${environment.documentService}/folder`, folderRequest);
  }

  deleteFolder(folderId: number): Observable<any> {
    return this.http.delete<any>(`${environment.documentService}/folder/${folderId}`);
  }

}
