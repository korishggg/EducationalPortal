import {Component, OnInit} from '@angular/core';
import {ManagerService} from "../../service/api/manager.service";
import {User} from "../../modules/User";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {CreateGroupModalComponent} from "../groups/create-group-modal/create-group-modal.component";
import {ViewDocumentsModalComponent} from "./view-documents-modal/view-documents-modal.component";

@Component({
  selector: 'app-users-control',
  templateUrl: './users-control.component.html',
  styleUrls: ['./users-control.component.scss']
})
export class UsersControlComponent implements OnInit {

  approvedUsers: User[] = [];
  unApprovedUsers: User[] = [];
  showApprovedUsers: boolean = false;
  isLoading = true;

  constructor(private managerService: ManagerService, private modalService: NgbModal,) {
  }

  ngOnInit(): void {
    this.fetchUnApprovedUsers();
  }

  fetchApprovedUsers() {
    this.isLoading = true;
    this.managerService.getApprovedUsers().subscribe(
      users => {
        this.approvedUsers = users;
        this.showApprovedUsers = true;
      }, error => {
        console.log(error);
      }, () => {
        this.isLoading = false;
      }
    );
  }

  fetchUnApprovedUsers() {
    this.isLoading = true;
    this.managerService.getUnapprovedUsers().subscribe(
      users => {
        this.unApprovedUsers = users;
        this.showApprovedUsers = false;
      }, error => {
        console.log(error);
      }, () => {
        this.isLoading = false;
      }
    );
  }

  approveUser(userId: number) {
    this.managerService.approveUser(userId.toString()).subscribe(
      () => {
        this.unApprovedUsers = this.unApprovedUsers.filter(user => user.id !== userId);
      },
      error => {
        console.log(error);
      }
    )
  }

  openViewDocumentsModal(userId: number) {
    const modal = this.modalService.open(ViewDocumentsModalComponent, {
      backdrop: 'static',
      size: 'xl'
    });
    const component = modal.componentInstance as ViewDocumentsModalComponent;
    component.userId = userId;
    modal.result
      .then(_ => {

      })
      .catch(() => {/** do nothing */
      })
  }

  assignInstructor(userId: number) {
    this.managerService.assignInstructor(userId.toString()).subscribe(
      () => {
        this.fetchApprovedUsers();
      }, error => {
        console.log(error);
      }
    )
  }
}
