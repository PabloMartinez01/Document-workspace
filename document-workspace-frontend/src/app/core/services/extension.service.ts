import {Injectable} from '@angular/core';
import {ExtensionConfiguration} from '../model/configuration/extension-configuration';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ExtensionService {

  private extensionsUrl = 'extensions.json';
  private extensionConfiguration: ExtensionConfiguration = {
    editableFormats: [],
    viewableFormats: [],
    documentFormats: [],
    slideFormats: [],
    spreadsheetFormats: [],
    formFormats: []
  }

  constructor(private http: HttpClient) {
    this.http.get<ExtensionConfiguration>(this.extensionsUrl).subscribe({
      next: extensions => {
        const keys: (keyof ExtensionConfiguration)[] = Object.keys(extensions) as (keyof ExtensionConfiguration)[]
        keys.forEach(key => {
          if (extensions[key] && Array.isArray(extensions[key])) {
            this.extensionConfiguration[key] = extensions[key].map(format => format.toLowerCase());
          }
        });
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
