import {Injectable} from "@angular/core";
import {AuthService} from "../../auth/auth.service";
import {Headers, Http, RequestOptions, Response} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {ParticipatedTrainingLearnModel} from "./learn-participated-training-model";

@Injectable()
export class LearnService {

  private startTrainingUrl = "api/participated/training/start/training/";

  private startNextLesson = "api/participated/training/";

  private startPreviousLesson = "api/participated/training/";

  constructor(private http: Http, private authService: AuthService) {

  }

  startTraining(trainingId: number): Observable<ParticipatedTrainingLearnModel> {
    let headers = new Headers({'Authorization': 'Bearer ' + this.authService.token});
    let options = new RequestOptions({headers: headers});
    let url = this.startTrainingUrl + trainingId;
    return this.http
      .get(url, options)
      .map((response: Response) => <ParticipatedTrainingLearnModel>response.json());
  }

  nextParticipatedLesson(participatedTrainingId: number): Observable<ParticipatedTrainingLearnModel> {
    let headers = new Headers({'Authorization': 'Bearer ' + this.authService.token});
    let options = new RequestOptions({headers: headers});
    let url = this.startNextLesson + participatedTrainingId + "/next/lesson";
    return this.http
      .get(url, options)
      .map((response: Response) => <ParticipatedTrainingLearnModel>response.json());
  }

  previousParticipatedLesson(participatedTrainingId: number): Observable<ParticipatedTrainingLearnModel> {
    let headers = new Headers({'Authorization': 'Bearer ' + this.authService.token});
    let options = new RequestOptions({headers: headers});
    let url = this.startPreviousLesson + participatedTrainingId + "/previous/lesson";
    return this.http
      .get(url, options)
      .map((response: Response) => <ParticipatedTrainingLearnModel>response.json());
  }
}

