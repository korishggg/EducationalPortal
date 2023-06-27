import {Component} from "@angular/core";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {CategoryService} from "../../../service/api/category.service";
import {Category} from "../../../modules/Category";

@Component({
  selector: 'app-confirmation-delete-modal',
  templateUrl: './confirmation-delete-category-modal.component.html',
})

export class ConfirmationDeleteCategoryModalComponent {

  category: Category;

  constructor(private modal: NgbActiveModal,
              private categoryService: CategoryService) {
  }

  closeModal() {
    this.modal.dismiss();
  }

  deleteCategory(categoryId: number) {
    this.categoryService.deleteCategory(categoryId).subscribe(
      response => {
        console.log(response);
      }, error => {
        console.log(error)
      }, () => {
        this.closeModal()
        window.location.reload();
      }
    )
  }

}
