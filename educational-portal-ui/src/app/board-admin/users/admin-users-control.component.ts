import {Component, OnInit} from "@angular/core";
import {UserService} from "../../service/api/user.service";
import {UserInfo} from "../../modules/UserInfo";
import {AdminService} from "../../service/api/admin.service";
import { Location } from '@angular/common';
import {
  DeleteUserConfirmationModalComponent
} from "./delete-user-confirmation-modal/delete-user-confirmation-modal.component";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {
  AssignManagerConfirmationModalComponent
} from "./assign-manager-confirmation-modal/assign-manager-confirmation-modal.component";

@Component({
  selector: 'app-admin-users-control',
  templateUrl: './admin-users-control.component.html',
  styleUrls: ['./admin-users-control.component.scss']
})

export class AdminUsersControlComponent implements OnInit {
  users: UserInfo[];

  constructor(private userService: UserService, private adminService: AdminService,
              private modalService: NgbModal) {
  }

  ngOnInit() {
    this.fetchAllUsersInfo();
  }

  fetchAllUsersInfo() {
    this.userService.getAllUsersInfo().subscribe(
      users => {
        this.users = users;
      },
      error => {
        console.log(error);
      }
    );
  }

  openDeleteConfirmationModal(userId: number) {
    const modal = this.modalService.open(DeleteUserConfirmationModalComponent, {
      backdrop: 'static',
      size: 's'
    });
    modal.componentInstance.user = {id: userId};
    modal.result
      .then(_ => {
        this.fetchAllUsersInfo()
      })
      .catch(() => {/** do nothing */
      })
  }

  openAssignManagerConfirmationModal(userId: number) {
    const modal = this.modalService.open(AssignManagerConfirmationModalComponent, {
      backdrop: 'static',
      size: 's'
    });
    modal.componentInstance.user = {id: userId};
    modal.result
      .then(_ => {
        this.fetchAllUsersInfo()
      })
      .catch(() => {
      })
  }
}
