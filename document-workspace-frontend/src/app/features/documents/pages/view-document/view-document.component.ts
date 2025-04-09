import {Component, OnDestroy, OnInit} from '@angular/core';
import {ConfigurationService} from '../../../../core/services/configuration.service';
import {DocumentEditorModule, IConfig} from '@onlyoffice/document-editor-angular';
import {ActivatedRoute, Router} from '@angular/router';
import {environment} from '../../../../../environments/environment';
import {Action} from '../../../../core/model/action.enum';
import {AuthenticationService} from '../../../../core/services/authentication.service';
import {Subject, takeUntil} from 'rxjs';
import {AlertService} from '../../../../core/services/alert.service';
import {Messages} from '../../../../core/model/messages/messages';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-view-document',
  standalone: true,
  imports: [
    DocumentEditorModule
  ],
  templateUrl: './view-document.component.html'
})
export class ViewDocumentComponent implements OnInit, OnDestroy {

  configuration: IConfig = {};
  id: number = -1;
  documentServer: string = `${environment.documentServer}/`

  private destroy$ = new Subject<void>();

  constructor(
    private activatedRoute: ActivatedRoute,
    private configurationService: ConfigurationService,
    private authenticationService: AuthenticationService,
    private alertService: AlertService,
    private router: Router ) {

  }


  ngOnInit(): void {
    this.authenticationService.tokenStatus$.pipe(
        takeUntil(this.destroy$)
    )
    .subscribe(expired => {
    if (expired) {
      this.alertService.showTimerAlert(
        Messages.sessionExpired.title,
        Messages.sessionExpired.body,
        "error",
        8000,
        (result) => {
          if (result.dismiss === Swal.DismissReason.timer) {
            this.router.navigate(['/login']).then();
          }
        }
      )
    }
  });

    const idParam = this.activatedRoute.snapshot.paramMap.get('id');
    if (!idParam || isNaN(Number(idParam))) return;
    this.id = parseInt(idParam);

    const action: Action = this.activatedRoute.snapshot.queryParams['action'] || Action.VIEW;

    this.configurationService.getConfiguration(this.id, action).subscribe({
      next: configuration => {
        this.configuration = configuration
      },
      error: err => console.log(err)
    })

  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  onDocumentReady = () => {
    console.log("Document is loaded")
  }

  onLoadComponentError = (errorCode: any, errorDescription: any) => {
    switch (errorCode) {
      case -1:
        console.log(errorDescription)
        break

      case -2:
        console.log(errorDescription)
        break

      case -3:
        console.log(errorDescription)
        break
    }
  }

}
