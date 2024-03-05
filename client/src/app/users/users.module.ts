import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserListComponent } from './user-list/user-list.component';
import { UserViewComponent } from './user-view/user-view.component';
import { UserCreateComponent } from './user-create/user-create.component';



@NgModule({
  declarations: [
    UserListComponent,
    UserViewComponent,
    UserCreateComponent
  ],
  imports: [
    CommonModule
  ]
})
export class UsersModule { }
