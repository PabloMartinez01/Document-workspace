import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {Folder} from '../model/folder/folder';
import {FolderRequest} from '../model/folder/folder-request';
import {FolderInfoResponse} from '../model/folder/folder-info-response';
import {FolderItemsResponse} from '../model/folder/folder-items-response';

@Injectable({
  providedIn: 'root'
})
export class FolderService {

  constructor(private http: HttpClient) {}

  getFolder(folderId: number): Observable<Folder> {
    return this.http.get<Folder>(environment.documentService + "/folder/" + folderId);
  }

  getFolderItemsByName(folderId: number, name: string) {
    return this.http.get<FolderItemsResponse>(environment.documentService + "/folder/" + folderId, {params: {name}})
  }

  createFolder(folderRequest: FolderRequest): Observable<FolderInfoResponse> {
    return this.http.post<FolderInfoResponse>(environment.documentService + "/folder", folderRequest);
  }


}
