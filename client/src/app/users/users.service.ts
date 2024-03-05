import {
  HttpClient,
  HttpErrorResponse,
  HttpStatusCode,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, map, retry, throwError } from 'rxjs';
import { User } from './user.model';
import { environment } from '../../environments/environment';
import { Group } from '../groups/group.model';
import { Expense } from '../expenses/expense.model';

interface UserDTO {
  id: number;
  name: string;
}

interface ExpenseDTO {
  id: number;
  payer: UserDTO;
  group: GroupDTO;
  name: string;
  amount: number;
  paymentDate: Date;
}

interface GroupDTO {
  id: number;
  name: string;
  members: UserDTO[];
  expenses: ExpenseDTO[];
}

@Injectable({
  providedIn: 'root',
})
export class UsersService {
  private usersUrl = environment.apiUrl + '/users';

  constructor(private http: HttpClient) {}

  getUsers(): Observable<User[]> {
    return this.http.get<UserDTO[]>(this.usersUrl).pipe(
      map((users: any[]) =>
        users.map((user) => {
          return this.convertToUser(user);
        }),
      ),
      retry(2),
      catchError(this.handleError),
    );
  }

  getUser(id: number): Observable<User> {
    return this.http
      .get<UserDTO>(`${this.usersUrl}/${id}`)
      .pipe(map((user) => this.convertToUser(user)));
  }

  addUser(name: string): Observable<User> {
    return this.http
      .post<UserDTO>(this.usersUrl, {
        name: name,
      })
      .pipe(map((user) => this.convertToUser(user)));
  }

  private convertToUser(user: UserDTO): User {
    return {
      id: user.id,
      name: user.name,
    };
  }

  private convertToGroup(group: GroupDTO): Group {
    return {
      id: group.id,
      name: group.name,
      members: group.members.map((m) => this.convertToUser(m)),
      expenses: group.expenses.map((e) => this.convertToExpense(e)),
    };
  }
  convertToExpense(e: ExpenseDTO): Expense {
    return {
      id: e.id,
      payer: this.convertToUser(e.payer),
      group: this.convertToGroup(e.group),
      name: e.name,
      amount: e.amount,
      paymentDate: e.paymentDate,
    };
  }

  getGroups(userId: number): Observable<Group[]> {
    return this.http.get<GroupDTO[]>(`${this.usersUrl}/${userId}/groups`).pipe(
    map((groups: any[]) =>
      groups.map((group) => {
        return this.convertToGroup(group);
      }),
    ),
      retry(2),
      catchError(this.handleError),
    );
  }

  private handleError(error: HttpErrorResponse) {
    switch (error.status) {
      case 0:
        console.error('Client error:', error.error);
        break;
      case HttpStatusCode.InternalServerError:
        console.error('Server error:', error.error);
        break;
      case HttpStatusCode.BadRequest:
        console.error('Request error:', error.error);
        break;
      default:
        console.error('Uknown error:', error.error);
    }
    return throwError(() => error);
  }
}
