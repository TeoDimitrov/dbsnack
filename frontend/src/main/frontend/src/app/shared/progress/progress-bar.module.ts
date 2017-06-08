import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {ProgressBarComponent} from "./progress-bar.component";
import {CommonModule} from "@angular/common";

@NgModule({
  declarations: [ProgressBarComponent],
  imports: [
    CommonModule,
    FormsModule,
    HttpModule,
  ],
  exports: [ProgressBarComponent]
})
export class ProgressBarModule {

}
