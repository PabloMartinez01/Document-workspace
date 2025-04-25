import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthenticationRequest} from '../model/authentication/authentication-request';
import {BehaviorSubject, distinctUntilChanged, interval, map, Observable} from 'rxjs';
import {AuthenticationResponse} from '../model/authentication/authentication-response';
import {environment} from '../../../environments/environment';
import {TokenStorageService} from './token-storage.service';
import {jwtDecode} from 'jwt-decode';
import {RegisterRequest} from '../model/authentication/register-request';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private readonly checkIntervalMs = 10000;

  private tokenStatusSubject = new BehaviorSubject<boolean>(false);
  tokenStatus$ = this.tokenStatusSubject.asObservable()

  constructor(private httpClient: HttpClient, private tokenStorageService: TokenStorageService) {
    this.startTokenStatusWatcher();
  }

  authenticate(authenticationRequest: AuthenticationRequest): Observable<AuthenticationResponse> {
    return this.httpClient.post<AuthenticationResponse>(environment.documentService + "/authenticate", authenticationRequest)
  }

  register(registerRequest: RegisterRequest): Observable<any> {
    return this.httpClient.post<any>(environment.documentService + "/register", registerRequest);
  }

  getToken(): string | null {
    return this.tokenStorageService.getToken();
  }

  setAuthentication(token: string): void {
    this.tokenStorageService.setToken(token)
  }

  isTokenValid(): boolean {
    try {
      const exp = this.getDecodedToken()?.exp;
      return exp ? exp * 1000 > Date.now() : false;
    } catch {
      return false;
    }
  }

  isAuthenticated(): boolean {
    return this.isTokenValid();
  }

  logout(): void {
    this.tokenStorageService.clearToken();
  }

  getUsername(): string | null {
    try {
      return this.getDecodedToken()?.sub || null;
    } catch {
      return null;
    }
  }

  private getDecodedToken(): any {
    return jwtDecode(this.tokenStorageService.getToken() || '');
  }

  private startTokenStatusWatcher(): void {
    interval(this.checkIntervalMs).pipe(
      map(() => this.isTokenValid()),
      distinctUntilChanged()
    ).subscribe((isValid) => {
      this.tokenStatusSubject.next(!isValid)
    });
  }


}
