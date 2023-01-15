import {Component, OnInit} from '@angular/core';
import {ManagerService} from "../../service/api/manager.service";
import {User} from "../../modules/User";

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

  constructor(private managerService: ManagerService) {
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
}
