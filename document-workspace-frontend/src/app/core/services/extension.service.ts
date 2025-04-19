import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ExtensionResponse} from '../model/extension/extension-response';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ExtensionService {

  constructor(private http: HttpClient) {

  }

  findExtensionByName(extension: string): Observable<ExtensionResponse> {
    return this.http.get<ExtensionResponse>(`${environment.documentService}/extension/${extension}`);
  }

}
