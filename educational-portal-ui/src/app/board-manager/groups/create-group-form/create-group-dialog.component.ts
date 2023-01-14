import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {GroupService} from "../../../service/api/group.service";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {User} from "../../../modules/User";
import {Category} from "../../../modules/Category";
import {forkJoin} from "rxjs";
import {CategoryService} from "../../../service/api/category.service";
import {ManagerService} from "../../../service/api/manager.service";

@Component({
  selector: 'app-create-group-dialog',
  templateUrl: './create-group-dialog.component.html',
  styleUrls: ['./create-group-dialog.component.scss']
})
export class CreateGroupDialogComponent implements OnInit {

  form = new FormGroup({
    "name": new FormControl("", Validators.required),
    "categoryId": new FormControl("", Validators.required),
    "instructorId": new FormControl("", Validators.required),
  });
  instructors: User[] = [];
  categories: Category[] = [];
  isLoading: boolean = true;

//TODO implement later categories and subcategories
  constructor(private activeModal: NgbActiveModal,
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
          console.log(this.instructors,this.categories);
        }
      })
  }

}
