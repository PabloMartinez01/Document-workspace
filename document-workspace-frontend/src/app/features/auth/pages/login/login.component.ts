import {Component} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {AuthenticationService} from '../../../../core/services/authentication.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './login.component.html'
})
export class LoginComponent {

  username: string = '';
  password: string = '';

  constructor(private authenticationService: AuthenticationService) {
  }

  onSubmit() {
    this.authenticationService.authenticate({username: this.username, password: this.password})
      .subscribe({
        next: response => {
          console.log(response);
        },
        error: err => console.log(err)
      })

  }

}
