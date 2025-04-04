import {Component} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {AuthenticationService} from '../../../../core/services/authentication.service';
import {Router} from '@angular/router';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule,
    NgIf
  ],
  templateUrl: './login.component.html'
})
export class LoginComponent {

  username: string = '';
  password: string = '';

  invalid: boolean = false;

  constructor(private authenticationService: AuthenticationService, private router: Router) {
  }

  onSubmit() {
    this.authenticationService.authenticate({username: this.username, password: this.password})
      .subscribe({
        next: authenticationResponse => {
          this.authenticationService.setToken(authenticationResponse.token);
          this.router.navigate(['folder/1']).then();
        },
        error: err => {
          console.log(err);
          this.invalid = true;
        }
      })

  }

}
