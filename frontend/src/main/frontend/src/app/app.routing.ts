import {Routes} from "@angular/router";
import {EditorGuard} from "./auth/editor.guard";
import {AuthGuard} from "./auth/auth.guard";

export const appRoutes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'home', loadChildren: 'app/home/home.module#HomeModule'},
  {path: 'trainings', loadChildren: 'app/trainings/trainings.module#TrainingsModule'},
  {path: 'learn', loadChildren: 'app/learn/learn.module#LearnModule', canActivate: [AuthGuard]},
  {path: 'error', loadChildren: 'app/error/error.module#ErrorModule'},
  {path: 'editor', loadChildren: 'app/editor/editor.module#EditorModule', canActivate: [EditorGuard]}
];
