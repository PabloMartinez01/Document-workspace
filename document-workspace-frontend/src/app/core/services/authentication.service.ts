import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthenticationRequest} from '../model/authentication/authentication-request';
import {BehaviorSubject, distinctUntilChanged, interval, map, Observable} from 'rxjs';
import {AuthenticationResponse} from '../model/authentication/authentication-response';
import {environment} from '../../../environments/environment';
import {TokenStorageService} from './token-storage.service';
import {jwtDecode} from 'jwt-decode';

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

  getToken(): string | null {
    return this.tokenStorageService.getToken();
  }

  setToken(token: string): void {
    return this.tokenStorageService.setToken(token)
  }

  isTokenValid(): boolean {
    const token = this.tokenStorageService.getToken();
    if (!token) return false;
    try {
      const decodedToken: any = jwtDecode(token);
      if (!decodedToken) return false;
      const expirationDate = decodedToken.exp * 1000;
      return expirationDate > Date.now();
    } catch (error) {
      return false;
    }
  }

  isAuthenticated(): boolean {
    return this.isTokenValid();
  }

  logout(): void {
    this.tokenStorageService.clearToken();
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
