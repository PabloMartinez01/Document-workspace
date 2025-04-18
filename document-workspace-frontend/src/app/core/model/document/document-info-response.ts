export interface DocumentInfoResponse {
  id: number;
  filename: string;
  extension: string;
  length: number;
  createdDate: string;
  lastModifiedDate: string;
  locked: boolean;
  version: number;
}
