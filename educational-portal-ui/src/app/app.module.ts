import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BoardAdminComponent } from './board-admin/board-admin.component';
import { BoardManagerComponent } from './board-manager/board-manager.component';
import { BoardInstructorComponent } from './board-instructor/board-instructor.component';
import { BoardUserComponent } from './board-user/board-user.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import {HttpClientModule} from "@angular/common/http";
import {authInterceptorProviders} from "./helpers/auth.interceptor";
import {UsersControlComponent} from "./board-manager/users/users-control.component";
import {GroupsControlComponent} from "./board-manager/groups/groups-control.component";
import { UploadDocumentsModalComponent } from "./board-user/upload-documents-modal/upload-documents-modal.component";
import {ViewDocumentsModalComponent} from "./board-manager/users/view-documents-modal/view-documents-modal.component";
import {CreateGroupModalComponent} from "./common/modals/create-group-modal/create-group-modal.component";
import {GroupsControlInstructorComponent} from "./board-instructor/groups/groups-control-instructor.component";
import {UpdateGroupModalComponent} from "./common/modals/add-user-to-group-modal/update-group-modal.component";
import {GroupChatModalComponent} from "./common/modals/group-chat/group-chat-modal.component";
import {CategoriesControlComponent} from "./board-manager/categories/categories-control.component";
import {
  CreateCategoryModalComponent
} from "./board-manager/categories/create-category-modal/create-category-modal.component";
import {
  AssignSubcategoryModalComponent
} from "./board-manager/categories/assign-subcategory-modal/assign-subcategory-modal.component";
import {
  ConfirmationDeleteCategoryModalComponent
} from "./board-manager/categories/confirmation-delete-category-modal/confirmation-delete-category-modal.component";
import {
  ConfirmationDeleteGroupModalComponent
} from "./board-manager/groups/confirmation-delete-group-modal/confirmation-delete-group-modal.component";
import {AdminUsersControlComponent} from "./board-admin/users/admin-users-control.component";
import {
  DeleteUserConfirmationModalComponent
} from "./board-admin/users/delete-user-confirmation-modal/delete-user-confirmation-modal.component";
import {
  AssignManagerConfirmationModalComponent
} from "./board-admin/users/assign-manager-confirmation-modal/assign-manager-confirmation-modal.component";
import {
  GroupsChatControlInstructorComponent
} from "./board-instructor/groups-chat/groups-chat-control-instructor.component";
import {WebSocketService} from "./service/api/websocket.service";
import {
  MembersOfTheGroupForChatComponent
} from "./common/modals/members-of-the-group-for-chat/members-of-the-group-for-chat.component";

@NgModule({
  declarations: [
    AppComponent,
    BoardAdminComponent,
    BoardManagerComponent,
    UsersControlComponent,
    GroupsControlComponent,
    BoardInstructorComponent,
    BoardUserComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    ProfileComponent,
    CreateGroupModalComponent,
    UploadDocumentsModalComponent,
    ViewDocumentsModalComponent,
    GroupsControlInstructorComponent,
    UpdateGroupModalComponent,
    GroupChatModalComponent,
    CategoriesControlComponent,
    CreateCategoryModalComponent,
    AssignSubcategoryModalComponent,
    ConfirmationDeleteCategoryModalComponent,
    ConfirmationDeleteGroupModalComponent,
    AdminUsersControlComponent,
    DeleteUserConfirmationModalComponent,
    AssignManagerConfirmationModalComponent,
    GroupsChatControlInstructorComponent,
    MembersOfTheGroupForChatComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [authInterceptorProviders,
              WebSocketService
  ],
  bootstrap: [AppComponent],
})
export class AppModule { }
