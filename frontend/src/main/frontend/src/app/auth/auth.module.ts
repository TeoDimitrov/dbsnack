import {NgModule} from "@angular/core";
import {LoginComponent} from "./login/login.component";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {RouterModule} from "@angular/router";
import {RegisterComponent} from "./register/register.component";
import {EqualValidator} from "./register/equal-validator.directive";
import {RegisterService} from "./register/register.service";
import {AuthService} from "./auth.service";
import {CookieService} from "angular2-cookie/core";
import {SocialComponent} from "./social/social.component";
import {MdlDialogService, MdlModule} from "angular2-mdl";
import {PasswordResetComponent} from "./password-reset/password-reset.component";
import {PasswordService} from "./password-reset/password.service";
import {AuthGuard} from "./auth.guard";
import {EditorGuard} from "./editor.guard";
import {AdminGuard} from "./admin.guard";
import {ProgressBarModule} from "../shared/progress/progress-bar.module";
import {CommonModule} from "@angular/common";

@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent,
    EqualValidator,
    SocialComponent,
    PasswordResetComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    HttpModule,
    MdlModule,
    ProgressBarModule,
    RouterModule.forRoot([
        {path: 'login', component: LoginComponent},
        {path: 'register', component: RegisterComponent},
        {path: 'social/authenticate', component: SocialComponent},
        {path: 'password/reset', component: PasswordResetComponent}
      ]
    )
  ],
  exports: [LoginComponent, RegisterComponent],
  providers: [RegisterService, AuthService, PasswordService, CookieService, MdlDialogService, AuthGuard, EditorGuard, AdminGuard],
})
export class AuthModule {

}
