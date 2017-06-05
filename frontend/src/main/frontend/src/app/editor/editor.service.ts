import {Injectable} from "@angular/core";
import {Headers, Http, RequestOptions, Response} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {TrainingEditModel} from "./training-edit";
import {AuthService} from "../auth/auth.service";
import {TopicEditModel} from "./topic-edit";
import {LessonEditModel} from "./lesson-edit";

@Injectable()
export class EditorService {

  private trainingsDataUrl = 'api/editor/trainings';

  private trainingsSaveUrl = 'api/editor/trainings/add';

  private trainingsUpdateUrl = 'api/editor/trainings/update/';

  private trainingsAddTopicUrl = 'api/editor/trainings/add/topic/';

  private topicUpdateUrl = 'api/editor/topics/update/';

  private topicAddLessonUrl = 'api/editor/topics/add/lesson/';

  private lessonUpdateUrl = 'api/editor/lessons/update/';


  constructor(private http: Http, private authService: AuthService) {

  }

  getTrainingsForEdit(): Observable<TrainingEditModel[]> {
    let headers = new Headers({'Authorization': 'Bearer ' + this.authService.token});
    let options = new RequestOptions({headers: headers});
    return this.http
      .get(this.trainingsDataUrl, options)
      .map((response: Response) => <TrainingEditModel[]>response.json())
  }

  addTraining(trainingEditModel: TrainingEditModel): Observable<Response> {
    let body = JSON.stringify(trainingEditModel);
    let headers = new Headers({
      'Authorization': 'Bearer ' + this.authService.token,
      'Content-Type': 'application/json',
      "Accept": "application/json"
    });
    let options = new RequestOptions({headers: headers});
    return this.http
      .post(this.trainingsSaveUrl, body, options);
  }

  updateTraining(trainingEditModel: TrainingEditModel): Observable<Response> {
    let body = JSON.stringify(trainingEditModel);
    let headers = new Headers({
      'Authorization': 'Bearer ' + this.authService.token,
      'Content-Type': 'application/json',
      "Accept": "application/json"
    });
    let options = new RequestOptions({headers: headers});
    return this.http
      .put(this.trainingsUpdateUrl + trainingEditModel.id, body, options);
  }

  addTopic(topicEditModel: TopicEditModel, selectedTrainingId: number): Observable<Response> {
    let body = JSON.stringify(topicEditModel);
    let headers = new Headers({
      'Authorization': 'Bearer ' + this.authService.token,
      'Content-Type': 'application/json',
      "Accept": "application/json"
    });
    let options = new RequestOptions({headers: headers});
    return this.http
      .post(this.trainingsAddTopicUrl + selectedTrainingId, body, options);
  }

  updateTopic(topicEditModel: TopicEditModel): Observable<Response> {
    let body = JSON.stringify(topicEditModel);
    let headers = new Headers({
      'Authorization': 'Bearer ' + this.authService.token,
      'Content-Type': 'application/json',
      "Accept": "application/json"
    });
    let options = new RequestOptions({headers: headers});
    return this.http
      .put(this.topicUpdateUrl + topicEditModel.id, body, options);
  }

  addLesson(lessonEditModel: LessonEditModel, selectedTopicId: number): Observable<Response> {
    let body = JSON.stringify(lessonEditModel);
    let headers = new Headers({
      'Authorization': 'Bearer ' + this.authService.token,
      'Content-Type': 'application/json',
      "Accept": "application/json"
    });
    let options = new RequestOptions({headers: headers});
    return this.http
      .post(this.topicAddLessonUrl + selectedTopicId, body, options);
  }

  updateLesson(lessonEditModel: LessonEditModel): Observable<Response> {
    let body = JSON.stringify(lessonEditModel);
    let headers = new Headers({
      'Authorization': 'Bearer ' + this.authService.token,
      'Content-Type': 'application/json',
      "Accept": "application/json"
    });
    let options = new RequestOptions({headers: headers});
    return this.http
      .put(this.lessonUpdateUrl + lessonEditModel.id, body, options);
  }
}
