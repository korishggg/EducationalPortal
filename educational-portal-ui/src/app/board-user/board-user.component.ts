import {Component, OnInit} from '@angular/core';
import {UserService} from "../service/api/user.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {UploadDocumentsModalComponent} from "./upload-documents-modal/upload-documents-modal.component";
import {Group} from "../modules/Group";
import {GroupService} from "../service/api/group.service";
import {GroupChatModalComponent} from "../common/modals/group-chat/group-chat-modal.component";

@Component({
  selector: 'app-board-user',
  templateUrl: './board-user.component.html',
  styleUrls: ['./board-user.component.scss']
})
export class BoardUserComponent implements OnInit {

  isCurrentUserApproved = false;
  isLoading = true;
  isDocumentsAreLoaded = false;
  groups: Group[];

  isAssignedToAnyGroup = false;

  constructor(private userService: UserService, private modalService: NgbModal, private groupService: GroupService) {
  }

  ngOnInit(): void {
    this.isLoading = true;
    this.userService.isCurrentUserApproved().subscribe(
      isApproved => {
        this.isCurrentUserApproved = isApproved;
        this.isCurrentUserExistsInAnyGroup();
      },
      error => {
        // TODO: Handle error
        console.log(error);
      },
      () => {
        if (!this.isCurrentUserApproved) {
          this.userService.isResourceForCurrentUserExistsForUser().subscribe(
            exists => {
              this.isDocumentsAreLoaded = exists;
              this.isLoading = false;
            },
            error => {
              // TODO: Handle error
              console.log(error);
              this.isLoading = false;
            }, () => {
              this.isLoading = false;
            }
          );
        }
      }
    );
  }

  uploadDocumentsModal() {
    const modal = this.modalService.open(UploadDocumentsModalComponent, {
      backdrop: 'static',
      size: 'l'
    });

    modal.result
      .then(_ => {
      })
      .catch(() => {/** do nothing */
      })
  }

  isCurrentUserExistsInAnyGroup() {
    this.userService.isCurrentUserAssignedToAnyGroup().subscribe(
      isAssignedToAnyGroup => {
        this.isAssignedToAnyGroup = isAssignedToAnyGroup;
      }, error => {
        console.log(error)
      }, () => {
        if (this.isAssignedToAnyGroup) {
          this.getAllGroupsForCurrentUser();
        }
      }
    )
  }

  getAllGroupsForCurrentUser() {
    this.groupService.getAllGroupsForCurrentUser(false).subscribe(
      groups => {
        this.groups = groups;
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
