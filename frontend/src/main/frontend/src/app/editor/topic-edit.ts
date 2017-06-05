import {LessonEditModel} from "./lesson-edit";

export class TopicEditModel {

  id: number;

  name: string;

  description: string;

  sequence: number;

  lessons: LessonEditModel[];

  constructor() {
    this.lessons = [];
  }
}
