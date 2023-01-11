import {Component, OnInit} from '@angular/core';
import {AuthService} from "../service/api/auth.service";
import {TokenStorageService} from "../service/token-storage.service";
import {AuthRequest} from "../modules/AuthRequest";
import {UserService} from "../service/api/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  form: any = {
    email: null,
    password: null
  };
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  role?: string = '';

  constructor(private router: Router,
              private authService: AuthService,
              private userService: UserService,
              private tokenStorage: TokenStorageService) {
  }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.role = this.tokenStorage.getUser()?.role;
    }
  }

  onSubmit(): void {
    const authRequest = new AuthRequest(this.form.email, this.form.password)
    this.authService.login(authRequest)
      .subscribe(authResponse => {
          this.tokenStorage.saveToken(authResponse.token);
          this.userService.getUserInfo()
            .subscribe(userInfo => {
                // TODO IMPLEMENT LATER REFRESH TOKEN LOGIC
                this.tokenStorage.saveUser(userInfo);

                this.isLoginFailed = false;
                this.isLoggedIn = true;
                this.role = this.tokenStorage.getUser()?.role;

                this.routeToRoleBasedHomeController();
              }
            )
        },
        err => {
          this.errorMessage = err.error.message;
          this.isLoginFailed = true;
        }
      );
  }

  reloadPage(): void {
    window.location.reload();
  }

  // TODO TEMPORARY SOLUTION LATER SHOULD BE IMPROVED WITH AUTO ROLE BASED ROUTING !
  private routeToRoleBasedHomeController() {
    let url;
    if (this.role === 'ADMIN_ROLE') {
      url = '/admin';
    } else if (this.role === 'MANAGER_ROLE') {
      url = '/manager';
    } else if (this.role === 'INSTRUCTOR_ROLE') {
      url = '/instructor';
    } else {
      url = '/user';
    }
    this.router.navigate([url])
      .then(() => {
        window.location.reload();
      })
  }
}
