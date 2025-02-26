import {Component, OnInit} from '@angular/core';
import {ConfigurationService} from '../../services/configuration.service';
import {DocumentEditorModule, IConfig} from '@onlyoffice/document-editor-angular';
import {ActivatedRoute} from '@angular/router';
import {environment} from '../../../../../environments/environment';
import {Action} from '../../model/action.enum';

@Component({
  selector: 'app-view-document',
  standalone: true,
  imports: [
    DocumentEditorModule
  ],
  templateUrl: './view-document.component.html'
})
export class ViewDocumentComponent implements OnInit {

  configuration: IConfig = {};
  id: number = -1;
  documentServer: string = `${environment.documentServer}/`

  constructor(
    private activatedRoute: ActivatedRoute,
    private configurationService: ConfigurationService
  ) {

  }


  ngOnInit(): void {

    const idParam = this.activatedRoute.snapshot.paramMap.get('id');
    if (!idParam || isNaN(Number(idParam))) return;
    this.id = parseInt(idParam);

    const action: Action = this.activatedRoute.snapshot.queryParams['action'] || Action.VIEW;

    this.configurationService.getConfiguration(this.id, action).subscribe({
      next: configuration => {
        console.log(configuration)
        this.configuration = configuration
      },
      error: err => {
        console.log(err)
      }
    })
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
