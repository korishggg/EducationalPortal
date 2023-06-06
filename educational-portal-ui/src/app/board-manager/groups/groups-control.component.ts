import {Component, OnInit} from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {CreateGroupModalComponent} from "../../modals/create-group-modal/create-group-modal.component";
import {Group} from "../../modules/Group";
import {GroupService} from "../../service/api/group.service";
import {forkJoin} from "rxjs";
import {CategoryService} from "../../service/api/category.service";
import {ManagerService} from "../../service/api/manager.service";
import {Category} from "../../modules/Category";
import {User} from "../../modules/User";

@Component({
  selector: 'app-groups-control',
  templateUrl: './groups-control.component.html',
  styleUrls: ['./groups-control.component.scss']
})
export class GroupsControlComponent implements OnInit {

  allGroups: Group[] = [];

  constructor(private modalService: NgbModal,
              private groupService: GroupService,
              private managerService: ManagerService,
              private categoryService: CategoryService) {
  }

  ngOnInit(): void {
    this.fetchAllGroups();
  }

  fetchAllGroups() {
    this.groupService.getAllGroups().subscribe(
      groups => {
        this.allGroups = groups;
      }, error => {
        console.log(error);
      }
    );
  }

  openCreateGroupModal() {
    let instructors: User[] = [];
    let categories: Category[] = [];

    forkJoin([this.categoryService.getAllCategories(), this.managerService.getAllInstructors()])
      .subscribe({
        next: ([categoriesResult, instructorsResult]: [Category[], User[]]) => {
          categories = categoriesResult;
          instructors = instructorsResult;
        },
        error: err => console.log(err),
        complete: () => {
          const modal = this.modalService.open(CreateGroupModalComponent, {
            backdrop: 'static',
            size: 'l'
          });
          modal.componentInstance.categories = categories;
          modal.componentInstance.instructors = instructors;

          modal.result
            .then(_ => {
            })
            .catch(() => {/** do nothing */
            })
        }
      })
  }
}
