import {Component} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators,} from '@angular/forms';
import {AuthenticationService} from '../../../../core/services/authentication.service';
import {Router} from '@angular/router';
import {NgIf} from '@angular/common';
import {AuthenticationRequest} from '../../../../core/model/authentication/authentication-request';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, NgIf, ReactiveFormsModule],
  templateUrl: './login.component.html',
})
export class LoginComponent {
  loginForm: FormGroup;
  invalid: boolean = false;

  constructor(
    private authenticationService: AuthenticationService,
    private formBuilder: FormBuilder,
    private router: Router,
  ) {
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]],
    });
  }

  get username() {
    return this.loginForm.get('username');
  }

  get password() {
    return this.loginForm.get('password');
  }

  onSubmit() {
    if (this.loginForm.invalid) return;

    const authenticationRequest = this.loginForm.value as AuthenticationRequest;
    this.authenticationService.authenticate(authenticationRequest).subscribe({
      next: (authenticationResponse) => {
        this.authenticationService.setAuthentication(authenticationResponse.token);
        this.router.navigate(['folder/1']).then();
      },
      error: (err) => {
        console.log(err);
        this.invalid = true;
      },
    });
  }
}
