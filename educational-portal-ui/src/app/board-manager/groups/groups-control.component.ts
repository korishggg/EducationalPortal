import {Component, OnInit} from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {Group} from "../../modules/Group";
import {GroupService} from "../../service/api/group.service";
import {CreateGroupDialogComponent} from "./create-group-form/create-group-dialog.component";


@Component({
  selector: 'app-groups-control',
  templateUrl: './groups-control.component.html',
  styleUrls: ['./groups-control.component.scss']
})

export class GroupsControlComponent implements OnInit {
  allGroups: Group[] = [];

  constructor(private groupService: GroupService, private modalService: NgbModal) {
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

  openForm() {
    const modal = this.modalService.open(CreateGroupDialogComponent, {size: "xl", backdrop: "static"});
    modal.result.then(() => console.log(modal.result))
      .catch(reason => console.log(reason))
  }

}
