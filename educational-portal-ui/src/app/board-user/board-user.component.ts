import {Component, OnInit} from '@angular/core';
import {UserService} from "../service/api/user.service";

@Component({
  selector: 'app-board-user',
  templateUrl: './board-user.component.html',
  styleUrls: ['./board-user.component.scss']
})
export class BoardUserComponent implements OnInit {

  isCurrentUserApproved = false;
  isLoading = true;

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
    this.userService.isCurrentUserApproved().subscribe(
      isApproved => {
        this.isCurrentUserApproved = isApproved;
      }, error => {
        // TODO LATER ADD COMMON ERROR NOTIFICATION
        console.log(error);
      }, () => {
        this.isLoading = false;
      }
    )

  }
}
