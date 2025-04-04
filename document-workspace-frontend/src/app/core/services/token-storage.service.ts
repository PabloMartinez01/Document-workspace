import {Injectable} from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  private readonly tokenKey = 'jwtToken';

  setToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

}
