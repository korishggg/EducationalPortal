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
  showAdminBoard = false;
  showManagerBoard = false;
  showInstructorBoard = false;
  showUserBoard = false;
  email?: string;

  constructor(private tokenStorageService: TokenStorageService) {
  }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.role = user?.role;
      this.email = user?.email;

      this.setupBoards();
    }
  }

  setupBoards() {
    if (this.role === 'ADMIN_ROLE') {
      this.showAdminBoard = true;
    } else if (this.role === 'MANAGER_ROLE') {
      this.showManagerBoard = true;
    } else if (this.role === 'INSTRUCTOR_ROLE') {
      this.showInstructorBoard = true;
    } else if (this.role === 'USER_ROLE') {
      this.showUserBoard = true;
    }
  }

  logout(): void {
    this.tokenStorageService.signOut();
    window.location.reload();
  }
}
