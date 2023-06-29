import {Component, OnInit} from "@angular/core";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {Group} from "../../../modules/Group";

@Component({
  selector: 'app-members-of-the-group-for-chat-modal',
  templateUrl: './members-of-the-group-for-chat.component.html',
})
export class MembersOfTheGroupForChatComponent implements OnInit {

  group: Group;

  constructor(private modal: NgbActiveModal) {
  }

  ngOnInit(): void {


  }

  closeModal() {
    this.modal.dismiss();
  }

}
