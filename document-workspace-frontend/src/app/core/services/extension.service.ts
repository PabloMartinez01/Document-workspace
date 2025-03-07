import {Injectable} from '@angular/core';
import {ExtensionConfiguration} from '../model/extension-configuration';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ExtensionService {

  private extensionsUrl = 'extensions.json';
  private extensionConfiguration: ExtensionConfiguration = {
    editableFormats: [],
    viewableFormats: []
  }

  constructor(private http: HttpClient) {
    this.http.get<ExtensionConfiguration>(this.extensionsUrl).subscribe({
      next: extensions => {
        this.extensionConfiguration = {
          editableFormats: extensions.editableFormats.map(ext => ext.toLowerCase()),
          viewableFormats: extensions.viewableFormats.map(ext => ext.toLowerCase()),
        }
      }
    })
  }

  isEditable(extension: string): boolean {
    return this.extensionConfiguration.editableFormats.includes(extension.toLowerCase());
  }

  isViewable(extension: string): boolean {
    return this.extensionConfiguration.viewableFormats.includes(extension.toLowerCase());
  }

  isSupported(extension: string): boolean {
    const lowerExtension = extension.toLowerCase();
    return this.isEditable(lowerExtension) || this.isViewable(lowerExtension);
  }






}
