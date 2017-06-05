import {NgModule} from "@angular/core";
import {TrainingsComponent} from "./trainings.component";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {TrainingsService} from "./trainings.service";
import {RouterModule} from "@angular/router";
import {TrainingDetailsComponent} from "./training-details/training-details.component";
import {TrainingDetailsService} from "./training-details/training-details.service";
import {ProgressBarModule} from "../shared/progress/progress-bar.module";

@NgModule({
  declarations: [
    TrainingsComponent,
    TrainingDetailsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    ProgressBarModule,
    RouterModule.forRoot([
      {path: 'trainings', component: TrainingsComponent},
      {path: 'trainings/:id/details', component: TrainingDetailsComponent}
    ])
  ],
  providers: [TrainingsService, TrainingDetailsService],
  exports: [TrainingsComponent]
})
export class TrainingsModule {

}
