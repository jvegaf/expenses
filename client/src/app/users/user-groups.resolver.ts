import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, ResolveFn } from '@angular/router';
import { UsersService } from './users.service';
import { Group } from '../groups/group.model';

export const userGroupsResolver: ResolveFn<Group[]> = (route: ActivatedRouteSnapshot) => {
  const usersService = inject(UsersService);

  const id = Number(route.paramMap.get('id'));
  return usersService.getGroups(id);
};
