import {Component, OnInit} from '@angular/core';
import {UserService} from "../service/api/user.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {UploadDocumentsModalComponent} from "./upload-documents-modal/upload-documents-modal.component";

@Component({
  selector: 'app-board-user',
  templateUrl: './board-user.component.html',
  styleUrls: ['./board-user.component.scss']
})
export class BoardUserComponent implements OnInit {

  isCurrentUserApproved = false;
  isLoading = true;
  isDocumentsAreLoaded = false;


  constructor(private userService: UserService, private modalService: NgbModal) {
  }

  ngOnInit(): void {
    this.isLoading = true;
    this.userService.isCurrentUserApproved().subscribe(
      isApproved => {
        this.isCurrentUserApproved = isApproved;
      },
      error => {
        // TODO: Handle error
        console.log(error);
      },
      () => {
        if (!this.isCurrentUserApproved) {
          this.userService.isResourceForCurrentUserExistsForUser().subscribe(
            exists => {
              this.isDocumentsAreLoaded = exists;
              this.isLoading = false;
            },
            error => {
              // TODO: Handle error
              console.log(error);
              this.isLoading = false;
            }, () => {
              this.isLoading = false;
            }
          );
        }
      }
    );
  }

  uploadDocumentsModal() {
    const modal = this.modalService.open(UploadDocumentsModalComponent, {
      backdrop: 'static',
      size: 'l'
    });

    modal.result
      .then(_ => {
      })
      .catch(() => {/** do nothing */
      })
  }
}
