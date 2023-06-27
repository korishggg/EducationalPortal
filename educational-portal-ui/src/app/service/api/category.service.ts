import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Category} from "../../modules/Category";
import {CreateCategoryRequest} from "../../modules/CreateCategoryRequest";
import {Observable} from "rxjs";
import {SubCategory} from "../../modules/SubCategory";

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

  createCategory(createCategoryRequest: CreateCategoryRequest): Observable<any> {
    return this.http.post(CATEGORY_API_URL, createCategoryRequest);

  }

  assignSubCategoryToCategory(categoryId: number, subcategoryId: number): Observable<any> {
    return this.http.post(CATEGORY_API_URL + categoryId + "/assignSubCategory/" + subcategoryId, null)
  }

  getAllSubCategories(): Observable<SubCategory[]> {
    return this.http.get<SubCategory[]>(CATEGORY_API_URL + "subCategories");
  }

  deleteCategory(categoryId: number): Observable<any> {
    return this.http.delete(CATEGORY_API_URL + categoryId);
  }

}

