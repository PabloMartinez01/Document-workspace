import {Type} from '../type.enum';

export interface ExtensionResponse {
  name: string;
  type: Type;
  actions: string[];
}
