import { Component, OnInit } from "@angular/core";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { ResourceService } from "../../../service/api/resource.service";
import { UserService } from "../../../service/api/user.service";
import { Resource } from "../../../modules/Resource";

@Component({
  selector: "app-view-documents-modal",
  templateUrl: "./view-documents-modal.component.html",
})
export class ViewDocumentsModalComponent implements OnInit {
  userId: number | null = null;
  isLoading = true;
  taxIdFiles: ArrayBuffer[] = [];
  passportFiles: ArrayBuffer[] = [];

  constructor(
    private modal: NgbActiveModal,
    private resourceService: ResourceService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    if (this.userId !== null) {
      this.fetchAllResourcesForUser(this.userId);
    }
  }

  closeModal() {
    this.modal.dismiss();
  }

  fetchAllResourcesForUser(userId: number) {
    this.isLoading = true;
    this.userId = userId;

    this.userService.getAllResourcesForUser(userId).subscribe(
      (resources: Resource[]) => {
        resources.forEach((resource) => {
          this.resourceService.getFileById(resource.id).subscribe(
            (data: ArrayBuffer) => {
              if (resource.type === "TAX_ID") {
                this.taxIdFiles.push(data);
              } else {
                this.passportFiles.push(data);
              }
            },
            (error) => {
              console.log(error);
            }
          );
        });
      },
      (error) => {
        console.log(error);
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  getBase64Image(file: ArrayBuffer): string {
    const base64String = btoa(
      new Uint8Array(file).reduce(
        (data, byte) => data + String.fromCharCode(byte),
        ""
      )
    );
    return "data:image/jpeg;base64," + base64String;
  }
}
