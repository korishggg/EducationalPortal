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

  constructor(private managerService: ManagerService) {
  }

  ngOnInit(): void {
    this.fetchUnApprovedUsers();
  }

  fetchApprovedUsers() {
    this.managerService.getApprovedUsers().subscribe(
      users => {
        this.approvedUsers = users;
        this.showApprovedUsers = true;
      }, error => {
        console.log(error);
      }
    );
  }

  fetchUnApprovedUsers() {
    this.managerService.getUnapprovedUsers().subscribe(
      users => {
        this.unApprovedUsers = users;
        this.showApprovedUsers = false;
      }, error => {
        console.log(error);
      }
    );
  }
}
