import {DocumentInfoResponse} from '../document/document-info-response';
import {FolderInfoResponse} from './folder-info-response';

export interface FolderItemsResponse {
  documents: DocumentInfoResponse[];
  folders: FolderInfoResponse[];
}
