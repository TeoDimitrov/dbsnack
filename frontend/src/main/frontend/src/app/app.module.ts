import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";

import {AppComponent} from "./app.component";
import {RouterModule} from "@angular/router";
import {HomeComponent} from "./home/home.component";
import {HomeModule} from "./home/home.module";
import {TrainingsModule} from "./trainings/trainings.module";
import {TrainingsComponent} from "./trainings/trainings.component";
import {AuthModule} from "./auth/auth.module";
import {ErrorModule} from "./error/error.module";
import {HashLocationStrategy, LocationStrategy} from "@angular/common";
import {EditorModule} from "./editor/editor.module";
import {LearModule} from "./learn/learn.module";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    HomeModule,
    TrainingsModule,
    AuthModule,
    ErrorModule,
    EditorModule,
    LearModule,
    RouterModule.forRoot([
        {path: '', component: HomeComponent}
      ]
    )
  ],
  providers: [{provide: LocationStrategy, useClass: HashLocationStrategy}],
  bootstrap: [AppComponent]
})
export class AppModule {
}
