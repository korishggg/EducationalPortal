import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BoardAdminComponent} from './board-admin/board-admin.component';
import {BoardManagerComponent} from './board-manager/board-manager.component';
import {BoardInstructorComponent} from './board-instructor/board-instructor.component';
import {BoardUserComponent} from './board-user/board-user.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HomeComponent} from './home/home.component';
import {ProfileComponent} from './profile/profile.component';
import {HttpClientModule} from "@angular/common/http";
import {authInterceptorProviders} from "./helpers/auth.interceptor";
import {UsersControlComponent} from "./board-manager/users/users-control.component";
import {GroupsControlComponent} from "./board-manager/groups/groups-control.component";
import {
  CreateGroupDialogComponent
} from "./board-manager/groups/create-group-form/create-group-dialog.component";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";

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
    CreateGroupDialogComponent,
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule {}
