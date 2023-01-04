import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {RegistrationRequest} from "../../modules/RegistrationRequest";
import {AuthRequest} from "../../modules/AuthRequest";
import {AuthResponse} from "../../modules/AuthResponse";

const AUTH_API = 'http://localhost:8080/auth/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) { }

  login(authRequest: AuthRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(AUTH_API + 'signIn', authRequest, httpOptions);
  }

  register(registrationRequest: RegistrationRequest): Observable<any> {
    return this.http.post(AUTH_API + 'signUp', registrationRequest, httpOptions);
  }
}
