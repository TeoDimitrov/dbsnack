import {LessonLearnModel} from "./learn-lesson-model";

export class ParticipatedLessonLearnModel {
  id: number;

  lastSubmission: string;

  lesson: LessonLearnModel;

  completed: boolean;
}
