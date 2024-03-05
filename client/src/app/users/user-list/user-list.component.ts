import { Component, OnInit } from '@angular/core';
import { User } from '../user.model';
import { UsersService } from '../users.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.scss'
})
export class UserListComponent implements OnInit {


    users: User[] = [];

    constructor(private usersService: UsersService) {}

    ngOnInit(): void {
      this.getUsers();
    }

    private getUsers(): void {
      this.usersService.getUsers().subscribe(users => this.users = users);
    }

}
