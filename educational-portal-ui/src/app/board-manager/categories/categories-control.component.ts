import {Component, OnInit} from "@angular/core";
import {Category} from "../../modules/Category";
import {CategoryService} from "../../service/api/category.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {CreateCategoryModalComponent} from "./create-category-modal/create-category-modal.component";
import {SubCategory} from "../../modules/SubCategory";
import {forkJoin} from "rxjs";
import {AssignSubcategoryModalComponent} from "./assign-subcategory-modal/assign-subcategory-modal.component";
import {ConfirmationDeleteCategoryModalComponent} from "./confirmation-delete-category-modal/confirmation-delete-category-modal.component";

@Component({
  selector: 'app-categories-control',
  templateUrl: './categories-control.component.html',
  styleUrls: ['./categories-control.component.scss']
})
export class CategoriesControlComponent implements OnInit {
  allCategories: Category[];

  subCategories: SubCategory[];
  showCategories: boolean = false;
  isLoading = true;

  constructor(private categoryService: CategoryService, private modalService: NgbModal) {
  }

  ngOnInit() {
    this.fetchAllCategories();
  }

  fetchAllCategories() {
    this.isLoading = true;
    this.categoryService.getAllCategories(true).subscribe(
      allCategories => {
        this.allCategories = allCategories;
        this.showCategories = true;
      }, error => {
        console.log(error);
      }, () => {
        this.isLoading = false;
      }
    )
  }

  fetchAllSubCategories() {
    this.isLoading = true;
    this.categoryService.getAllSubCategories().subscribe(
      allSubcategories => {
        this.subCategories = allSubcategories;
        this.showCategories = false;
      }, error => {
        console.log(error)
      }, () => {
        this.isLoading = false;
      }
    )
  }

  openCreateCategoryModal() {
    const modal = this.modalService.open(CreateCategoryModalComponent, {
      backdrop: 'static',
      size: 'l'
    });

    modal.result
      .then((createdCategory: Category) => {
        if (createdCategory) {
          this.allCategories.push(createdCategory);
        }
      })
      .catch(() => {/** do nothing */
      });
  }

  openAssignSubCategoryModal(category: Category) {
    let categories: Category[] = [];

    forkJoin([
      this.categoryService.getAllCategories(true)
    ]).subscribe(([categoriesResult]: [Category[]]) => {
      categories = categoriesResult;

      const modal = this.modalService.open(AssignSubcategoryModalComponent, {
        backdrop: 'static',
        size: 'l'
      });
      modal.componentInstance.categories = categories;
      modal.componentInstance.subcategory = category;
      modal.result
        .then(_ => {
        })
        .catch(() => {
        });
    }, error => {
      console.log(error)
    });
  }

  openConfirmationModal(categoryId: number) {
    const modal = this.modalService.open(ConfirmationDeleteCategoryModalComponent, {
      backdrop: 'static',
      size: 's'
    });
    modal.componentInstance.category = {id: categoryId};
    modal.result
      .then(_ => {
        this.fetchAllCategories()
      })
      .catch(() => {/** do nothing */
      })
  }

}
