import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {BoardUserComponent} from "./board-user/board-user.component";
import {BoardManagerComponent} from "./board-manager/board-manager.component";
import {BoardAdminComponent} from "./board-admin/board-admin.component";
import {BoardInstructorComponent} from "./board-instructor/board-instructor.component";
import {HomeComponent} from "./home/home.component";
import {ProfileComponent} from "./profile/profile.component";

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'user', component: BoardUserComponent },
  { path: 'manager', component: BoardManagerComponent },
  { path: 'instructor', component: BoardInstructorComponent },
  { path: 'admin', component: BoardAdminComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
