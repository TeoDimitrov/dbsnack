import {Component, OnDestroy, OnInit} from "@angular/core";
import {Subscription} from "rxjs/Subscription";
import {Router} from "@angular/router";
import {AuthUtil} from "../auth.util";
import {PasswordService} from "./password.service";
import {ProgressBarComponent} from "../../shared/progress/progress-bar.component";

@Component({
  templateUrl: './password-reset.component.html',
  styleUrls: ['./password-reset.component.css']
})
export class PasswordResetComponent implements OnInit, OnDestroy {

  private passwordResetSubscription: Subscription;

  username: string;

  isWrongEmail: boolean;

  constructor(private router: Router,
              private passwordService: PasswordService) {
  }

  ngOnInit(): void {
    this.username = "";
    AuthUtil.hideFooter();
    ProgressBarComponent.hideProgressBar();
  }

  ngOnDestroy(): void {
    AuthUtil.showFooter();
    if (this.passwordResetSubscription) {
      this.passwordResetSubscription.unsubscribe();
    }
  }

  onSubmit(): void {
    ProgressBarComponent.showProgressBar();
    this.passwordService
      .resetPassword(this.username)
      .subscribe(result => {
          if (result) {
            this.router.navigate(['login'], {queryParams: {isPasswordReset: true}});
          }
        },
        error => {
          this.isWrongEmail = true;
          ProgressBarComponent.hideProgressBar();
        },
        () => {
          ProgressBarComponent.hideProgressBar();
        });
  }
}
