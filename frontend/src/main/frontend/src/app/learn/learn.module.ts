import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {RouterModule} from "@angular/router";
import {RdbmsComponent} from "./rdbms/rdbms.component";
import {AuthGuard} from "../auth/auth.guard";
import {BenchmarkService} from "./rdbms/benchmark.service";
import {LearnService} from "./rdbms/learn.service";
import {KeysPipe} from "./rdbms/keys.pipe";
import {CompleteComponent} from "./complete/complete.component";
import {ProgressBarModule} from "../shared/progress/progress-bar.module";
import {CommonModule} from "@angular/common";
import {learnRoutes} from "./learn.routing";

@NgModule({
  declarations: [RdbmsComponent, KeysPipe, CompleteComponent],
  imports: [
    CommonModule,
    FormsModule,
    HttpModule,
    ProgressBarModule,
    RouterModule.forChild(learnRoutes)
  ],
  providers: [BenchmarkService, LearnService],
  exports: [RdbmsComponent]
})

export class LearnModule {

}
