import {Component, OnInit} from '@angular/core';
import {AuthService} from '../service/api/auth.service';
import {RegistrationRequest} from "../modules/RegistrationRequest";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  form: any = {
    firstName: null,
    lastName: null,
    email: null,
    password: null,
    phone: null
  };
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';

  constructor(private authService: AuthService) {
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
    const registrationRequest = new RegistrationRequest(this.form.firstName,
                                                        this.form.lastName,
                                                        this.form.password,
                                                        this.form.email,
                                                        this.form.phone);

    this.authService.register(registrationRequest)
      .subscribe(
        data => {
          this.isSuccessful = true;
          this.isSignUpFailed = false;
        },
        err => {
          this.errorMessage = err.error.message;
          this.isSignUpFailed = true;
        }
      );
  }
}
