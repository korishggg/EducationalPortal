import {Component} from '@angular/core';
import {UserService} from "../service/api/user.service";
import {TokenStorageService} from "../service/token-storage.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {

  isLoggedIn = false;

  constructor(private userService: UserService,
              private tokenStorageService: TokenStorageService) {
  }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
  }
}
