import {AfterViewChecked, Component} from "@angular/core";
import {AuthService} from "./auth/auth.service";
import {Router} from "@angular/router";

declare var componentHandler: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],

})
export class AppComponent implements AfterViewChecked{

  constructor(private router: Router, public authService: AuthService) {

  }

  ngAfterViewChecked(): void {
    this.updateGmlComponents();
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['']);
  }

  private updateGmlComponents(): void {
    if (componentHandler) {
      componentHandler.upgradeAllRegistered();
    }
  }
}
