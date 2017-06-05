import {Component, OnDestroy, OnInit} from "@angular/core";
import {AuthService} from "../auth.service";
import {Subscription} from "rxjs/Subscription";
import {LoginModel} from "./login-model";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthUtil} from "../auth.util";
import {MdlDialogService} from "angular2-mdl";
import {ProgressBarComponent} from "../../shared/progress/progress-bar.component";

@Component({
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {

  private isRegistered: boolean;

  private isActivated: boolean;

  private isEmailTaken: boolean;

  private isPasswordReset: boolean;

  private loginSubscription: Subscription;

  private returnUrl: string;

  loginModel: LoginModel;

  isWrongCredentials: boolean;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private authService: AuthService,
              private dialogService: MdlDialogService) {
  }

  ngOnInit(): void {
    this.loginModel = new LoginModel();
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    this.isRegistered = this.route.snapshot.queryParams['isRegistered'];
    this.isActivated = this.route.snapshot.queryParams['isActivated'];
    this.isEmailTaken = this.route.snapshot.queryParams['isEmailTaken'];
    this.isPasswordReset = this.route.snapshot.queryParams['isPasswordReset'];
    if (this.isRegistered) {
      this.dialogService.alert('We\'ve sent you an email to activate your account.');
    }

    if (this.isActivated) {
      this.dialogService.alert('Your account is successfully activated.');
    }

    if (this.isEmailTaken) {
      this.dialogService.alert('Your email is already in use.');
    }

    if (this.isPasswordReset) {
      this.dialogService.alert('Your password is reset. Check your email.');
    }

    AuthUtil.hideFooter();
    ProgressBarComponent.hideProgressBar();
  }

  ngOnDestroy(): void {
    AuthUtil.showFooter();
    if (this.loginSubscription) {
      this.loginSubscription.unsubscribe();
    }
  }

  onSubmit(): void {
    ProgressBarComponent.showProgressBar();
    this.loginSubscription =
      this.authService
        .login(this.loginModel.username, this.loginModel.password, this.loginModel.isRememberMe)
        .subscribe(result => {
            if (result === true) {
              this.router.navigate([this.returnUrl]);
            }
          },
          error => {
            this.isWrongCredentials = true;
            ProgressBarComponent.hideProgressBar();
          },
          () => {
            ProgressBarComponent.hideProgressBar();
          });
  }
}
