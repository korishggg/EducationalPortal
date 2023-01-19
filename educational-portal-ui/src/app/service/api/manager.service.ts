import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import {User} from "../../modules/User";

const API_URL = 'http://localhost:8080/manager/';

@Injectable({
  providedIn: 'root'
})
export class ManagerService {

  constructor(private http: HttpClient) { }

  getUnapprovedUsers(): Observable<User[]> {
    return this.http.get<User[]>(API_URL + 'unapprovedUsers')
  }

  getApprovedUsers(): Observable<User[]> {
    return this.http.get<User[]>(API_URL + 'approvedUsers')
  }

  approveUser(userId: string): Observable<any> {
    return this.http.put<any>(API_URL + 'approveUser/' + userId, null)
  }

  assignInstructor(userId: string): Observable<any> {
    return this.http.put<any>(API_URL + 'assignInstructor/' + userId, null)
  }

  getAllInstructors() :Observable<User[]> {
    return this.http.get<User[]>(API_URL+ 'instructors')
  }
}
