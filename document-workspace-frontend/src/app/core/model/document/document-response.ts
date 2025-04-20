import {ExtensionResponse} from '../extension/extension-response';

export interface DocumentResponse {
  id: number;
  filename: string;
  length: number;
  extension: ExtensionResponse;
  createdDate: string;
  lastModifiedDate: string;
  locked: boolean;
  version: number;
}
