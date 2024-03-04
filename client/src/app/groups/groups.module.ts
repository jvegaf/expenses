import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GroupListComponent } from './group-list/group-list.component';
import { GroupViewComponent } from './group-view/group-view.component';
import { GroupCreateComponent } from './group-create/group-create.component';



@NgModule({
  declarations: [
    GroupListComponent,
    GroupViewComponent,
    GroupCreateComponent
  ],
  imports: [
    CommonModule
  ]
})
export class GroupsModule { }
