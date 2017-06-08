import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {RouterModule} from "@angular/router";
import {EditorComponent} from "./editor.component";
import {EditorService} from "./editor.service";
import {EditorGuard} from "../auth/editor.guard";
import {AuthModule} from "../auth/auth.module";
import {CommonModule} from "@angular/common";
import {editorRoutes} from "./editor.routes";

@NgModule({
  declarations: [
    EditorComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    HttpModule,
    RouterModule.forChild(editorRoutes)
  ],
  exports: [EditorComponent],
  providers: [EditorService]
})
export class EditorModule {

}
