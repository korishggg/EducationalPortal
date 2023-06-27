import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";


const ADMIN_API_URL = 'http://localhost:8080/admin/';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  constructor(private http: HttpClient) {
  }

  assignManager(userId: string): Observable<any> {
    return this.http.post(ADMIN_API_URL + "assignManager/" + userId, null)
  }
}
