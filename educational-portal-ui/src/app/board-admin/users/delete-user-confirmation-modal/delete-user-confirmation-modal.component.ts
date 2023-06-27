import {Component} from "@angular/core";
import {User} from "../../../modules/User";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {UserService} from "../../../service/api/user.service";

@Component({
  selector: 'app-delete-user-confirmation-modal',
  templateUrl: './delete-user-confirmation-modal.component.html',
})

export class DeleteUserConfirmationModalComponent {

  user: User;

  constructor(private modal: NgbActiveModal,
              private userService: UserService) {
  }

  closeModal() {
    this.modal.dismiss();
  }

  deleteUser(userId: number) {
    this.userService.deleteUser(userId).subscribe(
      response => {

      }, error => {
        console.log(error)
      }, () => {
        this.closeModal()
        window.location.reload();
      }
    )
  }
}
