import {Component} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {GroupService} from "../../../service/api/group.service";
import {ManagerService} from "../../../service/api/manager.service";
import {CategoryService} from "../../../service/api/category.service";
import {delay, forkJoin} from "rxjs";
import {Category} from "../../../modules/Category";
import {User} from "../../../modules/User";

@Component({
  selector: 'app-create-group-modal',
  templateUrl: './create-group-modal.component.html',
})
export class CreateGroupModalComponent {

  instructors: User[] = [];
  categories: Category[] = [];
  isLoading: boolean = true;

  constructor(private modal: NgbActiveModal,
              private groupService: GroupService,
              private managerService: ManagerService,
              private categoryService: CategoryService) {
  }

  ngOnInit() {
    forkJoin([this.categoryService.getAllCategories(), this.managerService.getAllInstructors()])
      .subscribe({
        next: ([categories, instructors]: [Category[], User[]]) => {
          this.categories = categories;
          this.instructors = instructors;
          delay(3)
        },
        error: err => console.log(err),
        complete: () => {
          this.isLoading = false;
          console.log(this.instructors,this.categories);
        }
      })
  }

  closeModal() {
    this.modal.dismiss();
  }

  createGroup() {
    console.log("create group action");
  }
}

