import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {Folder} from '../model/folder';

@Injectable({
  providedIn: 'root'
})
export class FolderService {

  constructor(private http: HttpClient) {}

  getFolder(folderId: number): Observable<Folder> {
    return this.http.get<Folder>(environment.documentService + "/folder/" + folderId);
  }


}
