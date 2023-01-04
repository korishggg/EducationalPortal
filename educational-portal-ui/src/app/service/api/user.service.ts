import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import {UserInfo} from "../../modules/UserInfo";
import {TokenStorageService} from "../token-storage.service";

const API_URL = 'http://localhost:8080/users/';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient,
              private tokenStorageService: TokenStorageService) { }

  getUserInfo(): Observable<UserInfo> {
    return this.http.get<UserInfo>(API_URL + 'userInfo')
  }
}
