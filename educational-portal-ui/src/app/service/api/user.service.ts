import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {UserInfo} from "../../modules/UserInfo";
import {Resource} from "../../modules/Resource";
import {User} from "../../modules/User";

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

  uploadDocuments(passportFiles: File[], taxIdFiles: File[]): Observable<any> {
    const formData = new FormData();

    for (let i = 0; i < passportFiles.length; i++) {
      formData.append("passportFiles", passportFiles[i]);
    }

    for (let i = 0; i < taxIdFiles.length; i++) {
      formData.append("taxIdFiles", taxIdFiles[i]);
    }

    const headers = new HttpHeaders().append("Accept", "application/json");
    return this.http.post(API_URL + "uploadDocuments", formData, {headers});
  }

  isResourceForCurrentUserExistsForUser(): Observable<boolean> {
    return this.http.get<boolean>(API_URL + "documentsExists")
  }
  getAllResourcesForUser(userId: number): Observable<Resource[]> {
    return this.http.get<Resource[]>(API_URL + userId + "/resources");
  }

  getAllInstructors() :Observable<User[]> {
    return this.http.get<User[]>(API_URL+ 'instructors')
  }
}
