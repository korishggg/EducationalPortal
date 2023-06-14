import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Category} from "../../modules/Category";

const CATEGORY_API_URL = 'http://localhost:8080/categories/';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpClient) {
  }

  getAllCategories(isHideSubCategories: boolean) {
    const isHideSubCategoriesString = isHideSubCategories.toString();
    return this.http.get<Category[]>(CATEGORY_API_URL + "?isHideSubCategories=" + isHideSubCategoriesString);
  }
}

