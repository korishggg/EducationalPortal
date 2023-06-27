import {Component} from "@angular/core";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {CategoryService} from "../../../service/api/category.service";
import {Category} from "../../../modules/Category";

@Component({
  selector: 'app-assign-subcategory-modal',
  templateUrl: './assign-subcategory-modal.component.html',
})

export class AssignSubcategoryModalComponent {

  categories: Category[];
  parentCategory: Category;
  subcategory: Category;

  selectedCategory: Category;

  constructor(private modal: NgbActiveModal,
              private categoryService: CategoryService) {
  }

  closeModal() {
    this.modal.dismiss();
  }

  assignSubCategoryToCategory() {
    this.selectedCategory = this.parentCategory;
    this.categoryService.assignSubCategoryToCategory(this.parentCategory.id, this.subcategory.id).subscribe(
      response => {
        console.log("Succesfully assigned subcategory")
      }, error => {
        console.log(error)
      }, () => {
        this.closeModal();
        window.location.reload();
      }
    )
  }

}
