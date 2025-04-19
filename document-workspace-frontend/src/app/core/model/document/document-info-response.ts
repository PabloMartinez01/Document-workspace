import {Type} from '../type.enum';

export interface DocumentInfoResponse {
  id: number;
  filename: string;
  extension: string;
  length: number;
  type: Type;
  createdDate: string;
  lastModifiedDate: string;
  locked: boolean;
  version: number;
}
