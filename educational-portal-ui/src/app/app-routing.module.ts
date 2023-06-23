import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {BoardUserComponent} from "./board-user/board-user.component";
import {BoardManagerComponent} from "./board-manager/board-manager.component";
import {BoardAdminComponent} from "./board-admin/board-admin.component";
import {BoardInstructorComponent} from "./board-instructor/board-instructor.component";
import {HomeComponent} from "./home/home.component";
import {ProfileComponent} from "./profile/profile.component";
import {UsersControlComponent} from "./board-manager/users/users-control.component";
import {GroupsControlComponent} from "./board-manager/groups/groups-control.component";
import {GroupsControlInstructorComponent} from "./board-instructor/groups/groups-control-instructor.component";
import {CategoriesControlComponent} from "./board-manager/categories/categories-control.component";
import {AdminUsersControlComponent} from "./board-admin/users/admin-users-control.component";
import {
  GroupsChatControlInstructorComponent
} from "./board-instructor/groups-chat/groups-chat-control-instructor.component";

// TODO split child routings and import them
const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'user', component: BoardUserComponent},
  {
    path: 'manager',
    component: BoardManagerComponent,
    children: [
      {
        path: 'users',
        component: UsersControlComponent
      },
      {
        path: 'groups',
        component: GroupsControlComponent
      },
      {
        path: 'categories',
        component: CategoriesControlComponent
      }
    ]
  },
  {
    path: 'instructor', component: BoardInstructorComponent,
    children: [
      {
        path: 'groups',
        component: GroupsControlInstructorComponent
      },
      {
        path: 'groupsChat',
        component: GroupsChatControlInstructorComponent
      }
    ]
  },
  {
    path: 'admin', component: BoardAdminComponent,
    children: [
      {
        path: 'users',
        component: AdminUsersControlComponent
      }
    ]
  },
  {path: '', redirectTo: 'home', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
