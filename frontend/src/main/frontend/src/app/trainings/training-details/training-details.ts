import {TopicDetails} from "app/trainings/training-details/topic-details";

export class TrainingDetails {

  id: number;

  name: string;

  description: string;

  technologyStack: string;

  level: string;

  requiredHours: number;

  image: string;

  topics: TopicDetails[];
}
