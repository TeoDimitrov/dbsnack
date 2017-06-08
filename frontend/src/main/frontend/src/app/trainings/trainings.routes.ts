import {Routes} from "@angular/router";
import {TrainingsComponent} from "./trainings.component";
import {TrainingDetailsComponent} from "app/trainings/training-details/training-details.component";

export const trainingsRoutes: Routes = [
  {path: '', component: TrainingsComponent},
  {path: ':id/details', component: TrainingDetailsComponent}
];
