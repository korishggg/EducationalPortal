import {Component} from "@angular/core";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {CategoryService} from "../../../service/api/category.service";
import {CreateCategoryRequest} from "../../../modules/CreateCategoryRequest";

@Component({
  selector: 'app-create-category-modal',
  templateUrl: './create-category-modal.component.html',
})

export class CreateCategoryModalComponent {
  createCategoryForm = new FormGroup({
    name: new FormControl("", [Validators.required])
  })

  constructor(private modal: NgbActiveModal,
              private categoryService: CategoryService) {
  }

  closeModal() {
    this.modal.dismiss();
  }

  createCategory(): void {
    const name = this.createCategoryForm.value.name as string;
    const createCategoryRequest = new CreateCategoryRequest(name);

    this.categoryService.createCategory(createCategoryRequest).subscribe(
      res => {

      },
      error => {
      },
      () => {
        this.closeModal();
        window.location.reload();
      });
  }

}
