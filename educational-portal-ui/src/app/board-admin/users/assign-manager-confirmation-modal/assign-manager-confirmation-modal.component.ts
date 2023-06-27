import {Component} from "@angular/core";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {AdminService} from "../../../service/api/admin.service";
import {UserInfo} from "../../../modules/UserInfo";

@Component({
  selector: 'app-assign-manager-confirmation-modal',
  templateUrl: './assign-manager-confirmation-modal.component.html',
})

export class AssignManagerConfirmationModalComponent {

  user: UserInfo;


  constructor(private modal: NgbActiveModal, private adminService: AdminService) {
  }

  closeModal() {
    this.modal.dismiss();
  }

  assignManager(userId: number) {
    this.adminService.assignManager(userId.toString()).subscribe(
      () => {
        console.log("User with id " + userId + " has been promoted to the manager role")
      },
      error => {
        console.log(error);
      }, () => {
        this.closeModal();
        window.location.reload();
      }
    );
  }

}
