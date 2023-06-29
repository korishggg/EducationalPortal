import {Component} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {GroupService} from "../../../service/api/group.service";
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
              private groupService: GroupService) {
  }

  closeModal() {
    this.modal.dismiss();
  }

  createGroup(): void {
    const name = this.createGroupForm.controls.name.value as string;
    const categoryId = this.createGroupForm.controls.categoryId.value as number;
    const instructorId: number = this.createGroupForm.controls.instructorId.value as number;
      const createGroupRequest = new CreateGroupRequest(name, categoryId, instructorId)

    this.groupService.createGroup(createGroupRequest)
      .subscribe(
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

