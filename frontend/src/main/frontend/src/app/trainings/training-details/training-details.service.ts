import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {TrainingDetails} from "./training-details";
import {Http} from "@angular/http";
import "rxjs/add/operator/map";

@Injectable()
export class TrainingDetailsService {

  constructor(private http: Http) {

  }

  getTrainingDetails(id: number): Observable<TrainingDetails> {
    return this.http
      .get(this.getTrainingDetailsDataUrl(id))
      .map(response => <TrainingDetails>response.json())
      .catch(error => Observable.throw(error.json().error)|| 'Server error');
  }

  private getTrainingDetailsDataUrl(id: number): string {
    return "api/trainings/" + id + "/details";
  }
}
