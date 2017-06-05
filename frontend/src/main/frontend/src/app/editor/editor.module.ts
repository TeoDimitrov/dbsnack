import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {RouterModule} from "@angular/router";
import {EditorComponent} from "./editor.component";
import {EditorService} from "./editor.service";
import {EditorGuard} from "../auth/editor.guard";
import {AuthModule} from "../auth/auth.module";

@NgModule({
  declarations: [
    EditorComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AuthModule,
    RouterModule.forRoot([
        {path: 'edit/trainings', component: EditorComponent, canActivate: [EditorGuard]}
      ]
    )
  ],
  exports: [EditorComponent],
  providers: [EditorService]
})
export class EditorModule {

}
