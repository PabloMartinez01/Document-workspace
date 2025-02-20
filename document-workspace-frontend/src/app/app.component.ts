import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {DocumentEditorModule} from '@onlyoffice/document-editor-angular';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, DocumentEditorModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {


}
