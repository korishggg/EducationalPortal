import {Component} from "@angular/core";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {Group} from "../../../modules/Group";
import {UserInfoModal} from "../../../modules/UserInfoModal";
import {GroupService} from "../../../service/api/group.service";
import {UserService} from "../../../service/api/user.service";

@Component({
  selector: 'app-add-user-to-group-modal',
  templateUrl: './update-group-modal.component.html',
})

export class UpdateGroupModalComponent {
  group: Group;
  matchedUsers: UserInfoModal[] = [];
  showModal = false;
  searchEmailOrSurname: string;

  constructor(private modal: NgbActiveModal,
              private groupService: GroupService,
              private userService: UserService) {
  }

  ngOnInit() {

  }

  closeModal() {
    this.modal.dismiss();
  }

  searchUsers() {
    if (this.searchEmailOrSurname.length >= 2) {
      this.userService.findByEmailOrSurname(this.searchEmailOrSurname, this.group.id).subscribe(
        users => {
          console.log(users);
          this.matchedUsers = users;
        },
        error => {
          console.log(error);
        }
      );
    }
  }

  addUserToGroup(user: UserInfoModal) {
    this.groupService.assignUserToGroup(this.group.id, user.id).subscribe(
      response => {
        console.log("User with this id " + user.id + "has been added to the group with id " + this.group.id);
      },
      error => {
        console.log(error);
      }, () => {
        this.refreshGroup();
      }
    );
  }

  refreshGroup() {
    this.groupService.getGroup(this.group.id).subscribe(
      response => {
        this.group = response;
      }
    );
  }

}
