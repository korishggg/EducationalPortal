import {Component} from "@angular/core";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {Group} from "../../../modules/Group";
import {GroupService} from "../../../service/api/group.service";

@Component({
  selector: 'app-confirmation-delete-group-modal',
  templateUrl: './confirmation-delete-group-modal.component.html',
})

export class ConfirmationDeleteGroupModalComponent {

  group: Group;

  constructor(private modal: NgbActiveModal,
              private groupService: GroupService) {
  }

  closeModal() {
    this.modal.dismiss();
  }

  deleteGroup(groupId: number) {
    this.groupService.deleteGroup(groupId).subscribe(
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
