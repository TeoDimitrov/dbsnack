import {Component, OnDestroy, OnInit} from "@angular/core";
import {RegisterModel} from "./register-model";
import {RegisterService} from "./register.service";
import {Subscription} from "rxjs/Subscription";
import {Router} from "@angular/router";
import {AuthUtil} from "../auth.util";
import {ProgressBarComponent} from "../../shared/progress/progress-bar.component";

@Component({
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit, OnDestroy {

  registerModel: RegisterModel;

  registerSubscription: Subscription;

  isUsernameTaken: boolean;

  constructor(private registerService: RegisterService, private router: Router) {
  }

  ngOnInit(): void {
    this.registerModel = new RegisterModel();
    AuthUtil.hideFooter();
    ProgressBarComponent.hideProgressBar();
  }

  ngOnDestroy(): void {
    if (this.registerSubscription) {
      this.registerSubscription.unsubscribe();
    }

    AuthUtil.showFooter();
  }

  onSubmit() {
    if (this.registerModel.password !== this.registerModel.confirmPassword) {
      return;
    }

    ProgressBarComponent.showProgressBar();

    this.registerSubscription =
      this.registerService.registerUser(this.registerModel).subscribe(
        response => {
          this.router.navigate(['login'], {queryParams: {isRegistered: true}});
        },
        error => {
          switch (error.status) {
            case 400:
              this.isUsernameTaken = true;
              break;
          }

          ProgressBarComponent.hideProgressBar();
        },
        () => {
          ProgressBarComponent.hideProgressBar();
        }
      )
  }
}
