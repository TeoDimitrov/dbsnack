import {Injectable} from "@angular/core";
import {Headers, Http, RequestOptions, Response} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {JwtHelper} from "angular2-jwt";
import {UserDetails} from "./login/user-details";
import {Router} from "@angular/router";

@Injectable()
export class AuthService {

  private authUrl = 'api/authenticate';

  private roleAdmin = 'ROLE_ADMIN';

  private roleEditor = 'ROLE_EDITOR';

  private jwtHelper: JwtHelper;

  private userDetails: UserDetails;

  private roles: string[];

  token: string;

  constructor(private http: Http, private router:Router) {
    let currentUser = JSON.parse(localStorage.getItem('currentUser'));
    this.token = currentUser && currentUser.token;
    this.jwtHelper = new JwtHelper();
    this.checkTokenExpirationDate();
    this.decodeTokenData();
  }

  login(username: string, password: string, isRememberMe: boolean): Observable<boolean> {
    let bodyString = JSON.stringify({username, password, isRememberMe});
    let headers = new Headers({'Content-Type': 'application/json', "Accept": "application/json"});
    let options = new RequestOptions({headers: headers});
    return this
      .http
      .post(this.authUrl, bodyString, options)
      .map((response: Response) => {
          let token = response.json() && response.json().token;
          if (token) {
            this.token = token;
            localStorage.setItem('currentUser', JSON.stringify({token: token}));
            this.decodeTokenData();

            return true;
          } else {
            return false;
          }
        }
      );
  }

  logout(): void {
    this.token = null;
    localStorage.removeItem('currentUser');
    this.router.navigate(['']);
  }

  isAuthenticated(): boolean {
    return this.token ? true : false;
  }

  isRoleEditor(): boolean {
    if (!this.token) {
      return false;
    }

    for (let role of this.roles) {
      if (role === this.roleEditor) {
        return true;
      }
    }

    return false;
  }

  isRoleAdmin(): boolean {
    if (!this.token) {
      return false;
    }

    for (let role of this.roles) {
      if (role === this.roleAdmin) {
        return true;

      }
    }

    return false;
  }

  getUserId(): number {
    return this.userDetails.id;
  }

  getUserFullName(): string {
    return this.token ? this.userDetails.fullName : null;
  }

  decodeTokenData(): void {
    if (!this.token) {
      return;
    }

    let tokenData = this.jwtHelper.decodeToken(this.token);
    this.userDetails = tokenData.user_details;
    this.roles = tokenData.auth.split(',');
  }

  private checkTokenExpirationDate(): void {
    if (!this.token) {
      return;
    }

    if (this.jwtHelper.isTokenExpired(this.token)) {
      this.logout();
    }
  }
}
