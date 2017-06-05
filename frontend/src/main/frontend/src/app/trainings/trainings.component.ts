import {Component, OnDestroy, OnInit} from "@angular/core";
import {TrainingsService} from "./trainings.service";
import {Training} from "./training";
import {Subscription} from "rxjs/Subscription";
import {Router} from "@angular/router";
import {ProgressBarComponent} from "../shared/progress/progress-bar.component";
@Component({
  templateUrl: './trainings.component.html',
  styleUrls: ['./trainings.component.css']
})
export class TrainingsComponent implements OnInit, OnDestroy {

  trainings: Training[];

  trainingsSubscription: Subscription;

  constructor(private router: Router, private trainingsService: TrainingsService) {
  }

  ngOnInit(): void {
    this.trainingsSubscription =
      this.trainingsService
        .getTrainings()
        .subscribe(
          trainings => {
            this.trainings = trainings
          },
          error => {
            this.router.navigate(['error']);
          },
          () => {
            ProgressBarComponent.hideProgressBar();
          }
        );
  }

  ngOnDestroy(): void {
    if (this.trainingsSubscription) {
      this.trainingsSubscription.unsubscribe();
    }
  }
}
