import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {RouterModule} from "@angular/router";
import {ErrorComponent} from "./error.component";

@NgModule({
  declarations: [
    ErrorComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    RouterModule.forRoot([
        {path: 'error', component: ErrorComponent}
      ]
    )
  ],
  exports: [ErrorComponent]
})
export class ErrorModule {

}
