import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {RouterModule} from "@angular/router";
import {ErrorComponent} from "./error.component";
import {CommonModule} from "@angular/common";
import {errorRoutes} from "./error.routing";

@NgModule({
  declarations: [
    ErrorComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    HttpModule,
    RouterModule.forChild(errorRoutes)
  ],
  exports: [ErrorComponent]
})
export class ErrorModule {

}
