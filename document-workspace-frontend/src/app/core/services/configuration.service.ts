import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {IConfig} from '@onlyoffice/document-editor-angular';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ConfigurationService {

  constructor(private http: HttpClient) {

  }

  getConfiguration(id: number, action: string): Observable<IConfig> {
    const params = new HttpParams().set('action', action);
    return this.http.get<IConfig>(`${environment.documentService}/configuration/${id}`, { params });
  }

}
