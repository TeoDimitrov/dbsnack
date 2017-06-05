import {Component, OnDestroy, OnInit} from "@angular/core";
import {ParticipatedTrainingLearnModel} from "./learn-participated-training-model";
import {RelationalDatabaseResultModel} from "./db-relational-database-result-model";
import {ActivatedRoute, Router} from "@angular/router";
import {BenchmarkService} from "./benchmark.service";
import {LearnService} from "./learn.service";
import {Subscription} from "rxjs/Subscription";
import {ErrorQueryMessage} from "./db-error-query-message";
import {ProgressBarComponent} from "../../shared/progress/progress-bar.component";

@Component({
  templateUrl: './rdbms.component.html',
  styleUrls: ['./rdbms.component.css']
})
export class RdbmsComponent implements OnInit, OnDestroy {

  private initializeDataSqlQuery = "SELECT * FROM cookies";

  private learnSubscription: Subscription;

  private benchmarkSubscription: Subscription;

  participatedTraining: ParticipatedTrainingLearnModel;

  relationalDatabaseResult: RelationalDatabaseResultModel;

  sqlStatement: string;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private benchmarkService: BenchmarkService,
              private learnService: LearnService) {
  }

  ngOnInit(): void {
    this.participatedTraining = new ParticipatedTrainingLearnModel();
    this.relationalDatabaseResult = new RelationalDatabaseResultModel();
    let trainingId = this.route.snapshot.params['id'];
    this.learnSubscription =
      this.learnService
        .startTraining(trainingId)
        .subscribe(result => {
            this.participatedTraining = result;
            this.sqlStatement = this.participatedTraining.currentParticipatedLesson.lastSubmission;
            this.benchmarkSubscription =
              this.benchmarkService
                .benchmark(this.participatedTraining.id, this.initializeDataSqlQuery)
                .subscribe(
                  result => {
                    this.relationalDatabaseResult = result;
                    this.relationalDatabaseResult.message = null;
                  },
                  error => {
                    this.router.navigate(['error'])
                  },
                  () => {
                    ProgressBarComponent.hideProgressBar();
                  }
                )
          },
          error => this.router.navigate(['error'])
        );
  }

  ngOnDestroy(): void {
    if (this.learnSubscription) {
      this.learnSubscription.unsubscribe();
    }

    if (this.benchmarkSubscription) {
      this.benchmarkSubscription.unsubscribe();
    }
  }

  onNext(): void {
    ProgressBarComponent.showProgressBar();
    let participatedTrainingId = this.participatedTraining.id;
    this.learnSubscription =
      this.learnService
        .nextParticipatedLesson(participatedTrainingId)
        .subscribe(participatedTraining => {
            this.participatedTraining = participatedTraining;
            this.sqlStatement = this.participatedTraining.currentParticipatedLesson.lastSubmission;
            this.checkIfTrainingIsCompleted();
          },
          error => {
            this.router.navigate(['error'])
          },
          () => {
            ProgressBarComponent.hideProgressBar();
          });
    this.clearSqlResult();
  }

  onPrevious(): void {
    ProgressBarComponent.showProgressBar();
    let participatedTrainingId = this.participatedTraining.id;
    this.learnSubscription =
      this.learnService
        .previousParticipatedLesson(participatedTrainingId)
        .subscribe(participatedTraining => {
            this.participatedTraining = participatedTraining;
            this.sqlStatement = this.participatedTraining.currentParticipatedLesson.lastSubmission;
          },
          error => {
            this.router.navigate(['error'])
          },
          () => {
            ProgressBarComponent.hideProgressBar();
          }
        );
    this.clearSqlResult();
  }

  onSubmit(): void {
    ProgressBarComponent.showProgressBar();
    let participatedLessonId = this.participatedTraining.currentParticipatedLesson.id;
    this.benchmarkSubscription =
      this.benchmarkService
        .benchmark(participatedLessonId, this.sqlStatement)
        .subscribe(relationalDatabaseResult => {
            this.relationalDatabaseResult = relationalDatabaseResult;
            let isCorrect = this.relationalDatabaseResult.correct;
            if (isCorrect) {
              this.participatedTraining.currentParticipatedLesson.completed = true;
            }
          },
          error => {
            let errorQueryMessage = <ErrorQueryMessage>error.json();
            this.relationalDatabaseResult.message = errorQueryMessage.queryResult;
            ProgressBarComponent.hideProgressBar();
          },
          () => {
            ProgressBarComponent.hideProgressBar();
          });
  }

  private clearSqlResult(): void {
    this.relationalDatabaseResult.message = null;
  }

  private checkIfTrainingIsCompleted(): void {
    if (this.participatedTraining.completed === true) {
      let participatedTrainingId = this.participatedTraining.id;
      this.router.navigate(['learn/rdbms/training/' + participatedTrainingId + '/complete']);
    }
  }
}
