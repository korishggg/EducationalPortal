import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from "./service/token-storage.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  private role?: string;
  isLoggedIn = false;
  email?: string;

  constructor(private tokenStorageService: TokenStorageService) {
  }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.role = user?.role;
      this.email = user?.email;
    }
  }

  isUserHasRole(role: string) {
    const user = this.tokenStorageService.getUser();
    if (user) {
      return user.role == role;
    }
    return false;
  }

  logout(): void {
    this.tokenStorageService.signOut();
    window.location.reload();
  }
}
