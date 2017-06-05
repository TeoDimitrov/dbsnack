import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {ProgressBarComponent} from "./progress-bar.component";

@NgModule({
  declarations: [ProgressBarComponent],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
  ],
  exports: [ProgressBarComponent]
})
export class ProgressBarModule {

}
