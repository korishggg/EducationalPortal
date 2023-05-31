import {Component} from "@angular/core";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {UserService} from "../../service/api/user.service";

@Component({
  selector: 'app-upload-documents-modal',
  templateUrl: './upload-documents-modal.component.html',
})
export class UploadDocumentsModalComponent {
  passportFiles: File[] = [];
  taxIdFiles: File[] = [];

  constructor(
    private modal: NgbActiveModal,
    private userService: UserService
  ) {
  }

  closeModal(): void {
    this.modal.dismiss();
  }

  onPassportFileSelect(event: any): void {
    if (event.target.files && event.target.files.length > 0) {
      const files: FileList = event.target.files;
      this.passportFiles.push(...Array.from(files));
    }
  }

  onTaxIdFileSelect(event: any): void {
    if (event.target.files && event.target.files.length > 0) {
      const files: FileList = event.target.files;
      this.taxIdFiles.push(...Array.from(files));
    }
  }

  uploadFiles(): void {
    this.userService.uploadDocuments(this.passportFiles, this.taxIdFiles).subscribe(
      response => {
        console.log('Files uploaded successfully', response);
      },
      error => {
        console.error('Error occurred during file upload.', error);
      }
    );
    this.modal.close();
  }

  isFilesUploaded(): boolean {
    return !(this.passportFiles.length > 0 && this.taxIdFiles.length > 0);
  }
}
