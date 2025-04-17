import {Component, EventEmitter, Input, Output} from '@angular/core';
import {AuthenticationService} from '../../core/services/authentication.service';
import {Router} from '@angular/router';
import {NgIf, NgOptimizedImage} from '@angular/common';
import {MatIcon} from "@angular/material/icon";
import {MatIconButton} from "@angular/material/button";

@Component({
  selector: 'sidebar',
  standalone: true,
    imports: [
        NgIf,
        NgOptimizedImage,
        MatIcon,
        MatIconButton
    ],
  templateUrl: './sidebar.component.html'
})
export class SidebarComponent {

  @Input() hidden: boolean = true;
  @Output() hiddenChange: EventEmitter<boolean> = new EventEmitter<boolean>();

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

  hideSidebar(): void {
    this.hidden = true;
    this.hiddenChange.emit(true);
  }


}
