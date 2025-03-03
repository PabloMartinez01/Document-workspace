import {Injectable} from '@angular/core';
import {Client, Message} from '@stomp/stompjs';
import {Observable, Subject} from 'rxjs';
import {DocumentLockStatus} from '../model/document-lock-status';
import {environment} from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  private client: Client;
  private documentLockSubject: Subject<DocumentLockStatus> = new Subject();

  constructor() {
    this.client = new Client();
    this.client.configure({
      brokerURL: `${environment.documentService}/ws`,
      onConnect: () => {
        console.log('WebSocket connected')
        this.subscribeToDocumentLockStatusTopic();
      },
      onDisconnect:() => console.log('WebSocket disconnected')
    })
    this.client.activate();
  }

  private subscribeToDocumentLockStatusTopic(): void {
    this.client.subscribe('/topic/document/', (message: Message) => {
      this.documentLockSubject.next(JSON.parse(message.body) as DocumentLockStatus);
    })
  }

  getDocumentLockStatusTopic(): Observable<DocumentLockStatus> {
    return this.documentLockSubject.asObservable();
  }


}
