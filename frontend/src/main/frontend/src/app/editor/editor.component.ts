import {Component, OnDestroy, OnInit} from "@angular/core";
import {TrainingEditModel} from "./training-edit";
import {Subscription} from "rxjs/Subscription";
import {EditorService} from "./editor.service";
import {Router} from "@angular/router";
import {TopicEditModel} from "./topic-edit";
import {LessonEditModel} from "./lesson-edit";

@Component({
  templateUrl: './editor.component.html',
  styleUrls: ['./editor.component.css']
})
export class EditorComponent implements OnInit, OnDestroy {

  private trainingsEditSubscription: Subscription;

  trainings: TrainingEditModel[];

  selectedTraining: TrainingEditModel;

  selectedTopic: TopicEditModel;

  selectedLesson: LessonEditModel;

  constructor(private editorService: EditorService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.trainings = [];
    this.resetSelectedItems();
    this.getTrainings();
  }

  ngOnDestroy(): void {
    if (this.trainingsEditSubscription) {
      this.trainingsEditSubscription.unsubscribe();
    }
  }

  onSelectTraining(training: TrainingEditModel): void {
    this.selectedTraining = training;
    this.addIsDirty();
  }

  onSubmitTraining() {
    if (this.selectedTraining.id) {
      this.editorService
        .updateTraining(this.selectedTraining)
        .subscribe();
    } else {
      this.editorService
        .addTraining(this.selectedTraining)
        .subscribe(response => this.getTrainings());
      this.resetSelectedItems();
    }
  }

  onAddNewTraining(): void {
    this.selectedTraining = new TrainingEditModel();
    this.trainings.push(this.selectedTraining);
  }

  onSelectTopic(topic: TopicEditModel): void {
    this.selectedTopic = topic;
    this.addIsDirty();
  }

  onSubmitTopic() {
    if (this.selectedTopic.id) {
      this.editorService
        .updateTopic(this.selectedTopic)
        .subscribe();
    } else {
      this.editorService
        .addTopic(this.selectedTopic, this.selectedTraining.id)
        .subscribe(response => this.getTrainings());
      this.resetSelectedItems();
    }
  }

  onAddNewTopic(): void {
    this.selectedTopic = new TopicEditModel();
    this.selectedTraining.topics.push(this.selectedTopic);
  }

  onSelectLesson(lesson: LessonEditModel): void {
    this.selectedLesson = lesson;
    this.addIsDirty();
  }

  onSubmitLesson() {
    if (this.selectedLesson.id) {
      this.editorService
        .updateLesson(this.selectedLesson)
        .subscribe();
    } else {
      this.editorService
        .addLesson(this.selectedLesson, this.selectedTopic.id)
        .subscribe(response => this.getTrainings());
      this.resetSelectedItems();
    }
  }

  onAddNewLesson(): void {
    this.selectedLesson = new LessonEditModel();
    this.selectedTopic.lessons.push(this.selectedLesson);
  }

  private getTrainings(): void {
    this.editorService
      .getTrainingsForEdit()
      .subscribe(
        trainings => {
          this.trainings = trainings;
        },
        error => this.router.navigate(['error'])
      );
  }

  private addIsDirty(): void {
    let textFields = document.getElementsByClassName('mdl-textfield');
    for (var i = 0; i < textFields.length; i++) {
      textFields[i].classList.add('is-dirty');
    }
  }

  private resetSelectedItems(): void {
    this.selectedTraining = new TrainingEditModel();
    this.selectedTopic = new TopicEditModel();
    this.selectedLesson = new LessonEditModel();
  }
}
