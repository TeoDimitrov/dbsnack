import {Component, OnDestroy, OnInit} from "@angular/core";
import {TrainingDetailsService} from "./training-details.service";
import {TrainingDetails} from "./training-details";
import {Subscription} from "rxjs/Subscription";
import {ActivatedRoute, Router} from "@angular/router";
import {ProgressBarComponent} from "../../shared/progress/progress-bar.component";

@Component({
  templateUrl: './training-details.component.html',
  styleUrls: ['./training-details.component.css'],
})
export class TrainingDetailsComponent implements OnInit, OnDestroy {

  private trainingDetailsSubscription: Subscription;

  private routeDetailsSubscription: Subscription;

  trainingId: number;

  trainingDetails: TrainingDetails;

  constructor(private trainingDetailsService: TrainingDetailsService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    this.routeDetailsSubscription = this.route.params.subscribe(
      params => this.trainingId = +params['id']
    );

    this.trainingDetailsSubscription =
      this.trainingDetailsService.getTrainingDetails(this.trainingId)
        .subscribe(
          trainingDetails => {
            this.trainingDetails = trainingDetails;
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
    this.trainingDetailsSubscription.unsubscribe();
    this.routeDetailsSubscription.unsubscribe();
  }

  toggleTopic(event): void {
    let topicParent = event.target.parentNode;
    for (let child of topicParent.childNodes) {
      if (child.classList != undefined && child.classList.contains('topic-description'))
        if (child.classList.contains('invisible-paragraph')) {
          child.classList.remove('invisible-paragraph');
        } else {
          child.classList.add('invisible-paragraph');
        }
    }
  }

  toggleNameLesson(event): void {
    let lessonNode = event.target;
    this.toggleLesson(lessonNode);
  }

  toggleArrowLesson(event): void {
    //Go to lesson from icon lesson>h5>span>i
    let lessonNode = event.target.parentNode.parentNode;
    this.toggleLesson(lessonNode);
  }

  onLearn(): void {
    this.router.navigate(['learn/rdbms/training/' + this.trainingId])
  }

  private toggleLesson(lessonNode): void {
    //Lesson Name Click
    let lessonParent = lessonNode.parentNode;
    for (let parentChild of lessonParent.childNodes) {
      if (parentChild.classList != undefined && parentChild.classList.contains('lesson-description'))
        if (parentChild.classList.contains('invisible-paragraph')) {
          parentChild.classList.remove('invisible-paragraph');
        } else {
          parentChild.classList.add('invisible-paragraph');
        }
    }

    //Lesson Arrow Click
    let lessonChildren = lessonNode.childNodes;
    for (let lessonChild of lessonChildren) {
      if (lessonChild.classList != undefined && lessonChild.classList.contains('question__arrow')) {
        for (let arrowNode of lessonChild.childNodes) {
          //I stands for icon
          if (arrowNode.nodeName === 'I' && arrowNode.innerHTML === 'expand_less') {
            arrowNode.innerHTML = 'expand_more';
          } else {
            arrowNode.innerHTML = 'expand_less';
          }
        }
      }
    }
  }
}
