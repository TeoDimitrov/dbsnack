import {NgModule} from "@angular/core";
import {HomeComponent} from "./home.component";
import {RouterModule} from "@angular/router";
import {homeRoutes} from "./home.routes";

@NgModule({
  declarations: [
    HomeComponent
  ],
  imports: [
    RouterModule.forChild(homeRoutes)
  ],
  exports: [
    HomeComponent
  ]
})
export class HomeModule{

}
