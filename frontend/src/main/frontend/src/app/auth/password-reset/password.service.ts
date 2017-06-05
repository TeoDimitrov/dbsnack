import {Injectable} from "@angular/core";
import {Http, RequestOptions, Response, Headers} from "@angular/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class PasswordService {

  private resetPasswordUrl: string = "api/account/password/reset";

  constructor(private http: Http) {

  }

  resetPassword(username: String): Observable<Response> {
    let headers = new Headers({'Content-Type': 'application/json', "Accept": "application/json"});
    let options = new RequestOptions({headers: headers});
    return this.http
      .post(this.resetPasswordUrl, username, options);
  }
}
