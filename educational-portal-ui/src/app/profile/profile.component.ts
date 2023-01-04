import { Component } from '@angular/core';
import {TokenStorageService} from "../service/token-storage.service";
import {UserInfo} from "../modules/UserInfo";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent {

  currentUser?: UserInfo;
  accessToken?: string;

  constructor(private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    const userInfo = this.tokenStorageService.getUser();
    const token = this.tokenStorageService.getToken();

    if(userInfo != null) {
      this.currentUser = userInfo;
    }
    if (token != null) {
      this.accessToken = token;
    }
  }

}
