import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {Folder} from '../model/folder';
import {FolderRequest} from '../model/folder-request';
import {FolderInfo} from '../model/folder-info';
import {FolderItemsResponse} from '../model/folder-items-response';

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

  createFolder(folderRequest: FolderRequest): Observable<FolderInfo> {
    return this.http.post<FolderInfo>(environment.documentService + "/folder", folderRequest);
  }


}
