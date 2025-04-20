import {DocumentResponse} from "../document/document-response";
import {FolderInfoResponse} from "./folder-info-response";

export interface Folder {
    id: number;
    name: string;
    createdDate: string;
    lastModifiedDate: string;
    documents: DocumentResponse[];
    folders: FolderInfoResponse[];
    parentFolder: FolderInfoResponse;
}
