import {Injectable} from "@angular/core";
import {Headers, Http, RequestOptions, Response} from "@angular/http";
import {RelationalDatabaseResultModel} from "./db-relational-database-result-model";
import {Observable} from "rxjs/Observable";
import {AuthService} from "../../auth/auth.service";

@Injectable()
export class BenchmarkService {

  private benchmarkUrl = "api/benchmark/mysql/participated/lesson/";

  constructor(private http: Http, private authService: AuthService) {
  }

  benchmark(participatedLessonId: number, sqlQuery: string): Observable<RelationalDatabaseResultModel> {
    let headers = new Headers({'Authorization': 'Bearer ' + this.authService.token});
    let options = new RequestOptions({headers: headers});
    let url = this.benchmarkUrl + participatedLessonId;
    return this.http
      .post(url, sqlQuery, options)
      .map((response: Response) => <RelationalDatabaseResultModel>response.json());
  }
}
