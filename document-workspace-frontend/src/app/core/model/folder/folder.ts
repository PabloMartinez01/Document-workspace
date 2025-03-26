import {DocumentInfoResponse} from "../document/document-info-response";
import {FolderInfoResponse} from "./folder-info-response";

export interface Folder {
    id: number;
    name: string;
    documents: DocumentInfoResponse[];
    folders: FolderInfoResponse[];
    parentFolder: FolderInfoResponse;
}
