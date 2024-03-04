import { HttpClient, HttpErrorResponse, HttpStatusCode } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, retry, throwError } from 'rxjs';
import { User } from './user.model';
import { environment } from '../../environments/environment';

interface UserDTO {
  id: number;
  name: string;
}

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private usersUrl = environment.apiUrl + '/users';

  constructor(private http: HttpClient) { }

  getUsers(): Observable<User[]> {
    return this.http.get<UserDTO[]>(this.usersUrl).pipe(
      map(users => users.map(user => {
        return this.convertToUser(user);
      })),
      retry(2),
      catchError(this.handleError)
    );
  }

  getUser(id: number): Observable<User> {
    return this.http.get<UserDTO>(`${this.usersUrl}/${id}`).pipe(
      map(user => this.convertToUser(user))
    )
  }

  addUser(name: string): Observable<User> {
    return this.http.post<UserDTO>(this.usersUrl, {
      name: name,
    }).pipe(
      map(user => this.convertToUser(user))
    );
  }

  private convertToUser(user: UserDTO): User {
    return {
      id: user.id,
      name: user.name,
    };
  }

  private handleError(error: HttpErrorResponse) {
    switch(error.status) {
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
  }}
