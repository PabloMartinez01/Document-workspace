import {Component} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgIf} from "@angular/common";
import {Router, RouterLink} from '@angular/router';
import {AuthenticationService} from '../../../../core/services/authentication.service';
import {RegisterRequest} from '../../../../core/model/authentication/register-request';
import {ErrorResponse} from '../../../../core/model/error/error-response';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './register.component.html'
})
export class RegisterComponent {

  registerForm: FormGroup;
  invalid: boolean = false;
  errorMessage?: string;

  constructor(
    private formBuilder: FormBuilder,
    private authenticationService: AuthenticationService,
    private router: Router
    ) {
    this.registerForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(20)]],
      password: ['', [Validators.required, Validators.minLength(8)]],
    });
  }

  get username() {
    return this.registerForm.get('username');
  }

  get password() {
    return this.registerForm.get('password');
  }


  onSubmit() {

    if (this.registerForm.invalid) return;

    const registerRequest = this.registerForm.value as RegisterRequest;
    this.authenticationService.register(registerRequest).subscribe({
      next: () => {
        this.router.navigate(['/login']).then();
      },
      error: (error) => {
        this.invalid = true;
        this.errorMessage = (error.error as ErrorResponse).message;
      }
    })

  }

}
