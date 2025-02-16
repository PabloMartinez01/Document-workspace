import {Component, OnInit} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {DocumentEditorModule, IConfig} from '@onlyoffice/document-editor-angular';
import {environment} from '../environments/environment';



@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, DocumentEditorModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {

  private secret: string = "secret_key";
  documentServer: string = `http://${environment.documentServer}/`
  config: IConfig;

  constructor() {
    this.config = {
      document: {
        fileType: "docx",
        key: "bbba",
        title: "A.docx",
        url: `http://${environment.documentService}/document/1`,
      },
      documentType: "word",
      editorConfig: {
        "callbackUrl": `http://${environment.documentService}/document/callback`
      },
    };

    console.log(environment.secret)
  }




  onDocumentReady = () => {
    console.log("Document is loaded")
  }
  onLoadComponentError = (errorCode: any, errorDescription: any) => {
    switch (errorCode) {
      case -1: // Unknown error loading component
        console.log(errorDescription)
        break

      case -2: // Error load DocsAPI from http://documentserver/
        console.log(errorDescription)
        break

      case -3: // DocsAPI is not defined
        console.log(errorDescription)
        break
    }
  }






}
