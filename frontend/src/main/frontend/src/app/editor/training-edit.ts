import {TopicEditModel} from "./topic-edit";

export class TrainingEditModel{

  id:number;

  name:string;

  description:string;

  isActive:boolean;

  level: string;

  requiredHours: number;

  technologyStack:string;

  authorUsername:string;

  topics: TopicEditModel[];

  constructor() {
    this.topics = [];
  }
}
