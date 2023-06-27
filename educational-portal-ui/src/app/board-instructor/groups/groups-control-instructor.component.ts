import {Component, OnInit} from "@angular/core";
import {User} from "../../modules/User";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {GroupService} from "../../service/api/group.service";
import {UserService} from "../../service/api/user.service";
import {Group} from "../../modules/Group";
import {CreateGroupModalComponent} from "../../common/modals/create-group-modal/create-group-modal.component";
import {Category} from "../../modules/Category";
import {CategoryService} from "../../service/api/category.service";
import {forkJoin} from "rxjs";
import {GroupChatModalComponent} from "../../common/modals/group-chat/group-chat-modal.component";

@Component({
  selector: 'app-groups-instructor-control',
  templateUrl: './groups-control-instructor.component.html'
})
export class GroupsControlInstructorComponent implements OnInit {
  allGroupsForInstructor: Group[] = [];

  allCategories: Category[] = [];
  allInstructors: User[] = [];

  constructor(
    private modalService: NgbModal,
    private groupService: GroupService,
    private userService: UserService,
    private categoryService: CategoryService
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

  openCreateGroupModal() {
    let instructors: User[] = [];
    let categories: Category[] = [];

    const isHideSubCategories = true;

    forkJoin([
      this.categoryService.getAllCategories(isHideSubCategories),
      this.userService.getAllInstructors()
    ]).subscribe(([categoriesResult, instructorsResult]: [Category[], User[]]) => {
      categories = categoriesResult;
      instructors = instructorsResult;

      const modal = this.modalService.open(CreateGroupModalComponent, {
        backdrop: 'static',
        size: 'l'
      });
      modal.componentInstance.categories = categories;
      modal.componentInstance.instructors = instructors;

      modal.result
        .then(_ => {
        })
        .catch(() => {
        });
    }, error => console.log(error));
  }

  openChatModal() {
    const modal = this.modalService.open(GroupChatModalComponent, {
      backdrop: 'static',
      size: 'xl'
    });

    modal.result
      .then(_ => {})
      .catch(() => {})
  }
}
