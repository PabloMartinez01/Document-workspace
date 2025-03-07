import {DocumentInfo} from "./document-info";
import {FolderInfo} from "./folder-info";

export interface Folder {
    id: number;
    name: string;
    documents: DocumentInfo[];
    folders: FolderInfo[];
    parentFolder: FolderInfo[];
}
