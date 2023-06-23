import {Component, OnInit} from "@angular/core";
import {Group} from "../../modules/Group";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {GroupService} from "../../service/api/group.service";
import {GroupChatModalComponent} from "../../common/modals/group-chat/group-chat-modal.component";


@Component({
  selector: 'app-groups-chat-instructor-control',
  templateUrl: 'groups-chat-control-instructor.component.html'

})
export class GroupsChatControlInstructorComponent implements OnInit {
  allGroupsForInstructor: Group[] = [];

  constructor(
    private modalService: NgbModal,
    private groupService: GroupService
  ) {
  }

  ngOnInit(): void {
    this.getAllGroupsForCurrentUser();
  }

  getAllGroupsForCurrentUser() {
    this.groupService.getAllGroupsForCurrentUser(true).subscribe(
      groups => {
        this.allGroupsForInstructor = groups;
      }, error => {
        console.log(error);
      }
    );
  }
  openChatModal(group: Group) {
    const modal = this.modalService.open(GroupChatModalComponent, {
      backdrop: 'static',
      size: 'xl'
    });
    modal.componentInstance.group = group;
    modal.result
      .then(_ => {})
      .catch(() => {})
  }

}
