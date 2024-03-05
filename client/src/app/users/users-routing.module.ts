import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserListComponent } from './user-list/user-list.component';
import { UserViewComponent } from './user-view/user-view.component';
import { userGroupsResolver } from './user-groups.resolver';

const routes: Routes = [
  { path: 'users', component: UserListComponent },
  {
    path: 'users/:id/groups',
    component: UserViewComponent,
    resolve: {
      groups: userGroupsResolver
    }
  },
  { path: '', redirectTo: '/products', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductsRoutingModule { }
