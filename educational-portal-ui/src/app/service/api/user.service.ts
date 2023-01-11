import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import {UserInfo} from "../../modules/UserInfo";

const API_URL = 'http://localhost:8080/users/';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  getUserInfo(): Observable<UserInfo> {
    return this.http.get<UserInfo>(API_URL + 'userInfo')
  }

  isCurrentUserApproved(): Observable<boolean> {
    return this.http.get<boolean>(API_URL + 'isApproved')
  }
}
