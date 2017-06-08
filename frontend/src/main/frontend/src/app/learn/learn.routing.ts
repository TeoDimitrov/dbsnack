import {Routes} from "@angular/router";
import {AuthGuard} from "../auth/auth.guard";
import {RdbmsComponent} from "./rdbms/rdbms.component";
import {CompleteComponent} from "./complete/complete.component";

export const learnRoutes: Routes = [
  {path: 'rdbms/training/:id', component: RdbmsComponent},
  {path: 'rdbms/training/:id/complete', component: CompleteComponent}
];
