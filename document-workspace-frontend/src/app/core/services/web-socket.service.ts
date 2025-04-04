import {Injectable} from '@angular/core';
import {Client, Message} from '@stomp/stompjs';
import {Observable, Subject} from 'rxjs';
import {DocumentLockEvent} from '../model/document/document-lock-event';
import {environment} from '../../../environments/environment';
import {AuthenticationService} from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  private client: Client;
  private documentLockSubject: Subject<DocumentLockEvent> = new Subject();

  constructor(private authenticationService: AuthenticationService) {
    this.client = new Client();
    this.client.configure({
      brokerURL: `${environment.documentService}/ws?token=${authenticationService.getToken()}`,
      onConnect: () => {
        console.log('WebSocket connected')
        this.subscribeToDocumentLockTopic();
      },
      onDisconnect:() => console.log('WebSocket disconnected')
    })
    this.client.activate();
  }

  private subscribeToDocumentLockTopic(): void {
    this.client.subscribe('/topic/document/', (message: Message) => {
      this.documentLockSubject.next(JSON.parse(message.body) as DocumentLockEvent);
    })
  }

  getDocumentLockTopic(): Observable<DocumentLockEvent> {
    return this.documentLockSubject.asObservable();
  }


}
