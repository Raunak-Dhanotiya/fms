import { Injectable, EventEmitter } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';
import { TokenStorageService } from './tokenStorage.service';
import { CookieService } from 'ngx-cookie-service';
import { EnvService } from '../env.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  USER_KEY: string = 'auth-user';

  serverUrl = this.env.baseUrl;

  errorData: any = {};

  redirectUrl: string = "";

  loginStatus: EventEmitter<boolean> = new EventEmitter<boolean>(false);

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(
      private http: HttpClient,
      private router: Router,
      private tokenStorage: TokenStorageService,
      private cokieSrv: CookieService,
      private env: EnvService,
  ) { }

  login(email: string, password: string) {

    var body = {
      "username": email,
      "password": password
    };

    return this.http.post<any>(
        `${this.serverUrl}api/v1/auth/signin`,
        body,
        this.httpOptions
    );
  }

  register(data: any) {

    return this.http.post<any>(
        `${this.serverUrl}api/v1/auth/signup?t=${new Date().getTime()}`,
        data,
        this.httpOptions
    );
  }

  refreshToken() {

    let refreshAuth = this.getAuthorizationToken();

    let url: string = `${this.serverUrl}api/token`;

    let body = new FormData();

    body.append("grant_type", "refresh_token");

    body.append("refresh_token", refreshAuth);

    return this.http.post<any>(url, body).subscribe(token => {

      localStorage.setItem('token', JSON.stringify(token.access_token));

      localStorage.setItem('refresh_token', JSON.stringify(token.refresh_token));

      return token.access_token;

    });
  }

  isLoggedIn() {

    if (this.tokenStorage.getToken()) {
      return true;
    }

    return false;
  }

  getAuthorizationToken() {

    const currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}');

    return currentUser.access_token;
  }

  setPreviousUrl(url: any) {

    localStorage.setItem('previousUrl', url);
  }

  getPreviousUrl() {

    return localStorage.getItem('previousUrl');
  }

  getLoggedInUser() {

    if (sessionStorage.getItem('currentUser')) {

      const currentUser = JSON.parse(
          sessionStorage.getItem('currentUser') || '{}'
      );

      return currentUser.userName;
    }

    return '';
  }

  getLoggedInUserId() {

    const user = window.sessionStorage.getItem(this.USER_KEY);

    if (user) {

      let userData = JSON.parse(user);

      return userData.userId;
    }

    return '';
  }

  getLoggedInUserCompId() {

    const user = window.sessionStorage.getItem(this.USER_KEY);

    if (user) {

      let userData = JSON.parse(user);

      return userData.compId;
    }

    return 0;
  }

  getLoggedInUserEMId() {

    const user = window.sessionStorage.getItem(this.USER_KEY);

    if (user) {

      let userData = JSON.parse(user);

      return userData.emId;
    }

    return 0;
  }

  getLoggedInUserRole() {

    const user = window.sessionStorage.getItem(this.USER_KEY);

    if (user) {

      let userData = JSON.parse(user);

      return userData.userRoleId;
    }
  }

  getLoggedInTechnicianId() {

    const user = window.sessionStorage.getItem(this.USER_KEY);

    if (user) {

      let userData = JSON.parse(user);

      return userData.technicianId;
    }
  }

  logout() {

    this.tokenStorage.signOut();

    this.cokieSrv.deleteAll();

    this.router.navigate(['/login']);
  }

  private handleError(error: HttpErrorResponse) {

    if (error.error instanceof ErrorEvent) {

      console.error('An error occurred:', error.error.message);

    } else {

      console.error(
          `Backend returned code ${error.status}, ` +
          `body was: ${error.error}`
      );
    }

    this.errorData = {
      errorTitle: error.error.error,
      errorDesc: error.error.message,
      errorStatus: error.status
    };

    return throwError(this.errorData);
  }

  getLoggedInUserName() {

    const user = window.sessionStorage.getItem(this.USER_KEY);

    if (user) {

      let userData = JSON.parse(user);

      return userData.username;
    }

    return '';
  }
}
