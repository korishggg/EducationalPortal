import {Component} from "@angular/core";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'group-chat-modal',
  templateUrl: './group-chat-modal.component.html',
  styleUrls: ['./group-chat-modal.component.scss']
})
// This is common chat component here you should load all group info and messages for instructor and users (depends on which component been opened)
export class GroupChatModalComponent {

  constructor(private modal: NgbActiveModal) {
  }

  closeModal() {
    this.modal.dismiss();
  }

}
