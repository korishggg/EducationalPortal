import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Group} from "../../modules/Group";
import {CreateGroupRequest} from "../../modules/CreateGroupRequest";

const GROUP_API_URL = 'http://localhost:8080/groups/';

@Injectable({
  providedIn: 'root'
})
export class GroupService {

  constructor(private http: HttpClient) {
  }

  getAllGroups(): Observable<Group[]> {
    return this.http.get<Group[]>(GROUP_API_URL)
  }

  createGroup(createGroupRequest: CreateGroupRequest): Observable<any> {
    return this.http.post(GROUP_API_URL, createGroupRequest)
  }
}

