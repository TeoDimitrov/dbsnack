import {Injectable} from "@angular/core";
import "rxjs/add/operator/map";
import "rxjs/add/operator/catch";
import {Http, RequestOptions, Headers, Response} from "@angular/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class RegisterService {

  private trainingsDataUrl: string = "api/register";

  constructor(private http: Http) {

  }

  registerUser(registerModel): Observable<Response> {
    let bodyString = JSON.stringify(registerModel);
    let headers = new Headers({ 'Content-Type': 'application/json', "Accept": "application/json" });
    let options = new RequestOptions({ headers: headers });
    return this.http
      .post(this.trainingsDataUrl, bodyString, options);
  }
}
