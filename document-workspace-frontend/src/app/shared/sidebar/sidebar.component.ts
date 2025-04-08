import {Component} from '@angular/core';
import {AuthenticationService} from '../../core/services/authentication.service';
import {Router} from '@angular/router';
import {NgIf} from '@angular/common';

@Component({
  selector: 'sidebar',
  standalone: true,
  imports: [
    NgIf
  ],
  templateUrl: './sidebar.component.html'
})
export class SidebarComponent {

  constructor(
    private authenticationService: AuthenticationService,
    private router: Router
  ) {

  }

  logout(): void {
    this.authenticationService.logout();
    this.router.navigate(['login']).then();
  }

  isAuthenticated(): boolean {
    return this.authenticationService.isAuthenticated();
  }
}
