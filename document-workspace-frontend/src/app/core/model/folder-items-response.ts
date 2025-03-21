import {DocumentInfo} from './document-info';
import {FolderInfo} from './folder-info';

export interface FolderItemsResponse {
  documents: DocumentInfo[];
  folders: FolderInfo[];
}
