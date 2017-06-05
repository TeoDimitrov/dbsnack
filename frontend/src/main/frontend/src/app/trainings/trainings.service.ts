import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {Training} from "app/trainings/training";
import "rxjs/add/operator/map";
import "rxjs/add/operator/catch";

@Injectable()
export class TrainingsService {

  private trainingsDataUrl: string = "api/trainings";

  constructor(private http: Http) {
  }

  getTrainings(): Observable<Training[]> {
    return this.http.get(this.trainingsDataUrl)
      .map(response => <Training[]>response.json())
      .catch(error => Observable.throw(error.json().error || 'Server error')
      );
  }
}
