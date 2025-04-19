import {ExtensionResponse} from '../extension/extension-response';

export interface DocumentInfoResponse {
  id: number;
  filename: string;
  length: number;
  extension: ExtensionResponse;
  createdDate: string;
  lastModifiedDate: string;
  locked: boolean;
  version: number;
}
