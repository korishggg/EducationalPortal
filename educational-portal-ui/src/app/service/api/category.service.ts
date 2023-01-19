import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Category} from "../../modules/Category";

const CATEGORY_API_URL = 'http://localhost:8080/categories/';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpClient) {
  }
  getAllCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(CATEGORY_API_URL)
  }
}

