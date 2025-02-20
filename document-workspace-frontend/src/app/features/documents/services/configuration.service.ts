import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {IConfig} from '@onlyoffice/document-editor-angular';
import {environment} from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ConfigurationService {

  constructor(private http: HttpClient) {

  }

  getConfiguration(id: number): Observable<IConfig> {
    return this.http.get<IConfig>(environment.documentService + "/configuration/" + id)
  }

}
