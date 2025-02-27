import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

const API_URL = 'http://localhost:8080/resources/';

@Injectable({
  providedIn: 'root'
})
export class ResourceService {

  constructor(private http: HttpClient) {
  }

  getFileById(resourceId: number): Observable<ArrayBuffer> {
    const headers = new HttpHeaders({'Accept': 'application/octet-stream'});
    return this.http.get(API_URL + resourceId + "/file", {responseType: 'arraybuffer', headers: headers});
  }

}
