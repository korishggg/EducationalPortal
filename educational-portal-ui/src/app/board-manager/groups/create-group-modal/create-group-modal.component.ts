import {Component} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {GroupService} from "../../../service/api/group.service";
import {ManagerService} from "../../../service/api/manager.service";
import {CategoryService} from "../../../service/api/category.service";
import {delay, forkJoin} from "rxjs";
import {Category} from "../../../modules/Category";
import {User} from "../../../modules/User";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {CreateGroupRequest} from "../../../modules/CreateGroupRequest";

@Component({
  selector: 'app-create-group-modal',
  templateUrl: './create-group-modal.component.html',
})
export class CreateGroupModalComponent {
  createGroupForm = new FormGroup({
    name: new FormControl("", [Validators.required]),
    categoryId: new FormControl<number | null>(null, [Validators.required]),
    instructorId: new FormControl<number | null>(null, []),
  });
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
        },
        error: err => console.log(err),
        complete: () => {
          this.isLoading = false;
        }
      })
  }

  closeModal() {
    this.modal.dismiss();
  }

  createGroup(): void {
    const name = this.createGroupForm.value.name as string;
    const categoryId = this.createGroupForm.value.categoryId as number;
    const instructorId: number = this.createGroupForm.value.instructorId as number;
    const createGroupRequest = new CreateGroupRequest(name, categoryId, instructorId);

    this.groupService.createGroup(createGroupRequest)
      .subscribe(
        res => {

        },
        error => {
        },
        () => {
          this.closeModal();
        });
  }
}

