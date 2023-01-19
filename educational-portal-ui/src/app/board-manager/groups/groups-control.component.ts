import {Component, OnInit} from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {CreateGroupModalComponent} from "./create-group-modal/create-group-modal.component";
import {Group} from "../../modules/Group";
import {GroupService} from "../../service/api/group.service";

@Component({
  selector: 'app-groups-control',
  templateUrl: './groups-control.component.html',
  styleUrls: ['./groups-control.component.scss']
})
export class GroupsControlComponent implements OnInit {

  allGroups: Group[] = [];

  constructor(private modalService: NgbModal,
              private groupService: GroupService) {
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
    const modal = this.modalService.open(CreateGroupModalComponent, {
      backdrop: 'static',
      size: 'l'
    });

    modal.result
      .then(_ => {
        this.fetchAllGroups()
      })
      .catch(() => {/** do nothing */
      })
  }
}
